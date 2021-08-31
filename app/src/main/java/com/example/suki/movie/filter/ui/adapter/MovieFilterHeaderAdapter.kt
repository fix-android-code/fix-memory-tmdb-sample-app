package com.example.suki.movie.filter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suki.databinding.ItemMovieFilterHeaderBinding

class MovieFilterHeaderAdapter(
    private val onClickFilter: (View) -> Unit
) : RecyclerView.Adapter<MovieFilterHeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderViewHolder {
        val binding = ItemMovieFilterHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.binding.movieFilterChip.setOnClickListener { onClickFilter(it) }
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class HeaderViewHolder(val binding: ItemMovieFilterHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

}