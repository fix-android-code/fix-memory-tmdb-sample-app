package com.example.suki.movie.filter.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieFilterNetworkDto(

    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overview: String,
)