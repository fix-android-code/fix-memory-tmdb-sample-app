package com.example.suki.movie.filter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.example.suki.R
import com.example.suki.common.util.Constant.STATUS_WATCHING
import com.example.suki.databinding.FragmentMovieFilterBinding
import com.example.suki.main.activity.MainActivity
import com.example.suki.movie.filter.model.MovieFilterModel
import com.example.suki.movie.filter.ui.adapter.MovieFilterHeaderAdapter
import com.example.suki.movie.filter.ui.adapter.paging.MovieFilterLoadStateAdapter
import com.example.suki.movie.filter.ui.adapter.paging.MovieFilterPagingDataAdapter
import com.example.suki.movie.filter.ui.datastore.FilterType
import com.example.suki.movie.filter.ui.viewmodel.MovieFilterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@FlowPreview
@AndroidEntryPoint
class MovieFilterFragment : Fragment(), MovieFilterPagingDataAdapter.OnItemClickListener {

    private var _binding: FragmentMovieFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieFilterViewModel by viewModels()
    private lateinit var movieFilterPagingDataAdapter: MovieFilterPagingDataAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private var status: String = STATUS_WATCHING
    private val filterItems: Array<String> by lazy {
        arrayOf(
            getString(R.string.movie_filter_popular),
            getString(R.string.movie_filter_upcoming),
            getString(R.string.movie_filter_top_rated)
        )
    }
    lateinit var safeContext: Context
    private var filterDialogPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
        status = arguments?.getString("status") ?: STATUS_WATCHING
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun setup() {
        setPagingRecyclerView()
        setSwipeRefreshLayout()
        setMovieFilterPagingData()
        setFilterPositionToFilterTitle()
    }

    private fun setMovieFilterPagingData() {
        viewModel.movieFilterFlowPaging.observe(viewLifecycleOwner) {
            movieFilterPagingDataAdapter.submitData(lifecycle, it)
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener { movieFilterPagingDataAdapter.refresh() }
    }

    private fun setPagingRecyclerView() {
        val lottieDrawable = LottieDrawable()
        LottieCompositionFactory.fromRawRes(requireContext(), R.raw.image_loader)
            .addListener { lottieComposition ->
                lottieDrawable.composition = lottieComposition
                lottieDrawable.repeatCount = LottieDrawable.INFINITE
                lottieDrawable.playAnimation()
            }

        movieFilterPagingDataAdapter = MovieFilterPagingDataAdapter(
            this,
            lottieDrawable
        )
        val pagingHeaderAdapter =
            MovieFilterLoadStateAdapter { movieFilterPagingDataAdapter.retry() }
        val pagingFooterAdapter =
            MovieFilterLoadStateAdapter { movieFilterPagingDataAdapter.retry() }

        binding.movieFilterRecyclerView.adapter =
            movieFilterPagingDataAdapter.withLoadStateHeaderAndFooter(
                header = pagingHeaderAdapter,
                footer = pagingFooterAdapter
            )

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.movieFilterRecyclerView.layoutManager =
            linearLayoutManager

        movieFilterPagingDataAdapter.addLoadStateListener { loadState ->
            binding.movieFilterRecyclerView.isVisible =
                loadState.mediator?.refresh is LoadState.NotLoading || loadState.source?.refresh is LoadState.NotLoading

            binding.swipeRefreshLayout.isRefreshing =
                loadState.mediator?.refresh is LoadState.Loading && movieFilterPagingDataAdapter.itemCount == 0

            binding.errorStateLinearLayout.isVisible =
                loadState.mediator?.refresh is LoadState.Error && movieFilterPagingDataAdapter.itemCount == 0

            if (loadState.mediator?.refresh is LoadState.Error) {
                showSnackbar(getString(R.string.error_server))
            }
            binding.emptyImageView.isVisible =
                movieFilterPagingDataAdapter.itemCount == 0 && loadState.mediator?.refresh !is LoadState.Error && binding.errorStateLinearLayout.isVisible

            binding.retryButton.setOnClickListener {
                movieFilterPagingDataAdapter.retry()
            }
        }

        val headerAdapter = MovieFilterHeaderAdapter(
            onClickFilter = { showFilterDialog() }
        )

        // Order Matters
        val concatAdapter = ConcatAdapter(
            headerAdapter,
            movieFilterPagingDataAdapter,
            pagingHeaderAdapter,
            pagingFooterAdapter
        )
        binding.movieFilterRecyclerView.adapter = concatAdapter
    }

    private fun setFilterPositionToFilterTitle() {
        viewModel.filterType.observe(viewLifecycleOwner) {
            filterDialogPosition = it.ordinal
        }
    }

    private fun showFilterDialog() {

        MaterialAlertDialogBuilder(safeContext)
            .setTitle(getString(R.string.filter))
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.ok)) { _, _ ->

                when (filterDialogPosition) {
                    0 -> {
                        viewModel.onFilterOrderSelected(FilterType.POPULAR_MOVIE_FILTER)
                    }
                    1 -> {
                        viewModel.onFilterOrderSelected(FilterType.UPCOMING_MOVIE_FILTER)
                    }
                    2 -> {
                        viewModel.onFilterOrderSelected(FilterType.TOP_RATED_MOVIE_FILTER)
                    }
                    else -> {
                        viewModel.onFilterOrderSelected(FilterType.POPULAR_MOVIE_FILTER)
                    }
                }
            }
            .setSingleChoiceItems(filterItems, filterDialogPosition) { _, which ->
                filterDialogPosition = which
            }
            .show()
    }

    private fun showSnackbar(message: String?) {
        if (isAdded && message != null) {
            ((activity as? MainActivity)?.root ?: _binding?.root)?.let {
                Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onItemClick(searchResults: MovieFilterModel) {
        Timber.d("onItemClick: searchResults: $searchResults")
    }
}