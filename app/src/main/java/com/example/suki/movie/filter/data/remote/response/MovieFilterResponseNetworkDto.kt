package com.example.suki.movie.filter.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieFilterResponseNetworkDto(

    @SerializedName("page")
    val page: Int, // 2
    @SerializedName("results")
    val results: List<MovieFilterNetworkDto>,
    @SerializedName("total_pages")
    val totalPages: Int, // 500
    @SerializedName("total_results")
    val totalResults: Int // 10000
)