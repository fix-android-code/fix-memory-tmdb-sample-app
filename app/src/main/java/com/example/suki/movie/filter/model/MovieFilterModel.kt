package com.example.suki.movie.filter.model

data class MovieFilterModel(
    val itemId: Int,
    val posterPath: String?,
    val title: String,
    val genreIds: List<Int>,
    val voteAverage: Double,
    val overview: String,
) {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500${this.posterPath}"
    }
}