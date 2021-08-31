package com.example.suki.movie.filter.ui.adapter.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.example.suki.R
import com.example.suki.databinding.ItemMovieFilterPagingDataAdapterBinding
import com.example.suki.movie.filter.model.MovieFilterModel
import com.example.suki.movie.filter.ui.adapter.MovieFilterGenreRecyclerViewAdapter
import timber.log.Timber

class MovieFilterPagingDataAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<MovieFilterModel, MovieFilterPagingDataAdapter.MovieFilterViewHolder>(
    MovieFilterDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFilterViewHolder {

        val binding = ItemMovieFilterPagingDataAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFilterViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class MovieFilterViewHolder(
        private val binding: ItemMovieFilterPagingDataAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let {
                        listener.onItemClick(it)
                    }
                }
            }
        }

        fun bind(movieFilterModel: MovieFilterModel) {
            binding.movieFilterGenreFlexboxRecyclerView.visibility = View.VISIBLE
            binding.movieFilterGenreFlexboxRecyclerView.adapter = MovieFilterGenreRecyclerViewAdapter(
                movieFilterModel.genreIds.map {
                    it.toString()
                },
                object : MovieFilterGenreRecyclerViewAdapter.OverviewGenreListener {
                    override fun passSelectedGenre(genre: String) {
                        Timber.d("genre: $genre")
                    }
                })
            val lottieDrawable = LottieDrawable()
            LottieCompositionFactory.fromRawRes(itemView.context, R.raw.image_loader)
                .addListener { lottieComposition ->
                    lottieDrawable.composition = lottieComposition
                    lottieDrawable.repeatCount = LottieDrawable.INFINITE
                    lottieDrawable.playAnimation()
                }

            binding.apply {
                Glide
                    .with(itemView)
                    .load(movieFilterModel.getPosterUrl())
                    .placeholder(lottieDrawable)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(movieFilterImageView)

                movieFilterTitleTextView.text = movieFilterModel.title
                movieFilterCreatorTextView.text = movieFilterModel.overview
                movieFilterScoreTextView.text = movieFilterModel.voteAverage.toString()
                val randomFavorite = (0..9000).random()
                movieFilterFavoriteTextView.text = randomFavorite.toString()
                val listOfStrings = listOf("Watching", "Completed", "Dropped", "On hold", "Plan to watch")
                movieFilterUserStatusTextView.text = listOfStrings.random()
//                movieFilterTaglineTextView.text = movieFilterModel.posterPath
            }
        }
    }

    class MovieFilterDiffUtil : DiffUtil.ItemCallback<MovieFilterModel>() {
        override fun areItemsTheSame(
            oldItem: MovieFilterModel,
            newItem: MovieFilterModel
        ): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(
            oldItem: MovieFilterModel,
            newItem: MovieFilterModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(searchResults: MovieFilterModel)
    }
}
