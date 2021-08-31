package com.example.suki.movie.filter.ui.adapter.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.suki.databinding.ItemMovieFilterPagingLoadStateAdapterBinding

class MovieFilterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieFilterLoadStateAdapter.TppnaxzLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): TppnaxzLoadStateViewHolder {
        val binding =
            ItemMovieFilterPagingLoadStateAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return TppnaxzLoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: TppnaxzLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class TppnaxzLoadStateViewHolder(
        private val binding: ItemMovieFilterPagingLoadStateAdapterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.loadingStateItemRetryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.loadingStateItemErrorText.text = loadState.error.localizedMessage
            }
            binding.loadingStateItemProgressBar.isVisible = loadState is LoadState.Loading
            binding.loadingStateItemErrorContainer.isVisible = loadState is LoadState.Error
        }
    }
}
