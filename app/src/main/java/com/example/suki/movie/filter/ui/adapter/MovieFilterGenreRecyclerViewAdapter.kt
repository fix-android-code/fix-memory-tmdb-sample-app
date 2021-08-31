package com.example.suki.movie.filter.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suki.common.util.Constant
import com.example.suki.databinding.ItemMovieFilterGenreFlexboxRecyclerviewAdapterBinding

class MovieFilterGenreRecyclerViewAdapter(
    private val list: List<String?>,
    private val listener: OverviewGenreListener
) : RecyclerView.Adapter<MovieFilterGenreRecyclerViewAdapter.ViewHolder>() {

    interface OverviewGenreListener {
        fun passSelectedGenre(genre: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemMovieFilterGenreFlexboxRecyclerviewAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]!!

        val genreColor =
            Color.parseColor(
                if (Constant.GENRE_COLOR.containsKey(item)) {
                    Constant.GENRE_COLOR[item]
                } else {
                    Constant.DEFAULT_GENRE_COLOR
                }
            )
        holder.movieFilterGenreCard.setCardBackgroundColor(genreColor)
        holder.movieFilterGenreText.setTextColor(Color.parseColor("#FFFFFF"))
        holder.movieFilterGenreText.text = item
        holder.itemView.setOnClickListener {
            listener.passSelectedGenre(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(binding: ItemMovieFilterGenreFlexboxRecyclerviewAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val movieFilterGenreCard = binding.movieFilterGenreCard
        val movieFilterGenreText = binding.movieFilterGenreText
    }
}