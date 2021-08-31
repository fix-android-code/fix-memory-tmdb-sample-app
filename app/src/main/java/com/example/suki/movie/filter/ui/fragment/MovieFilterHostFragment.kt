package com.example.suki.movie.filter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.suki.R
import com.example.suki.common.util.Constant.STATUS_ALL
import com.example.suki.common.util.Constant.STATUS_COMPLETED
import com.example.suki.common.util.Constant.STATUS_WATCHING
import com.example.suki.databinding.FragmentMovieFilterHostBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@FlowPreview
class MovieFilterHostFragment : Fragment() {

    private var _binding: FragmentMovieFilterHostBinding? = null
    private val binding get() = _binding!!

    lateinit var safeContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieFilterHostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setToolbar()

        binding.movieFilterViewPager.adapter =
            MovieFilterPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(binding.movieFilterStatusTabLayout, binding.movieFilterViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.watching)
                }
                1 -> {
                    tab.text = getString(R.string.completed)
                }
                else -> {
                    tab.text = getString(R.string.all)
                }
            }
        }.attach()

    }

    private fun setToolbar() {
        binding.movieFilterMaterialToolbar.apply {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.movieFilterMenuPlayground1 -> {
                        Timber.d("movieFilterMenuPlayground1")
                        true
                    }
                    else -> false
                }
            }
            title = "MovieFilter"
        }
    }

    @FlowPreview
    inner class MovieFilterPagerAdapter(
        fm: FragmentManager,
        lf: Lifecycle
    ) : FragmentStateAdapter(fm, lf) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            val fragment = MovieFilterFragment()
            val bundle = Bundle()
            when (position) {
                0 -> bundle.putString("status", STATUS_WATCHING)
                1 -> bundle.putString("status", STATUS_COMPLETED)
                else -> bundle.putString("status", STATUS_ALL)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}