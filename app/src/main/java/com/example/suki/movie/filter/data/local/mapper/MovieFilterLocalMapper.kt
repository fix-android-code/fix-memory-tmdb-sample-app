package com.example.suki.movie.filter.data.local.mapper

import com.example.suki.movie.filter.data.local.entity.MovieFilterCacheEntity
import com.example.suki.movie.filter.model.MovieFilterModel

fun MovieFilterCacheEntity.mapMovieFilterCacheEntityToMovieFilterModel(): MovieFilterModel {
    return MovieFilterModel(
        itemId = itemId,
        posterPath = posterPath,
        title = title,
        genreIds = genreIds,
        voteAverage = voteAverage,
        overview = overview,

        )
}

fun MovieFilterModel.mapMovieFilterModelToMovieFilterCacheEntity(): MovieFilterCacheEntity {
    return MovieFilterCacheEntity(
        itemId = itemId,
        posterPath = posterPath,
        title = title,
        genreIds = genreIds,
        voteAverage = voteAverage,
        overview = overview,
    )
}

fun List<MovieFilterCacheEntity>.mapMovieFilterCacheEntityListToMovieFilterModelList(): List<MovieFilterModel> {
    return this.map {
        MovieFilterModel(
            itemId = it.itemId,
            posterPath = it.posterPath,
            title = it.title,
            genreIds = it.genreIds,
            voteAverage = it.voteAverage,
            overview = it.overview,
        )
    }
}

fun List<MovieFilterModel>.mapMovieFilterModelListToMovieFilterCacheEntityList(): List<MovieFilterCacheEntity> {
    return this.map {
        MovieFilterCacheEntity(
            itemId = it.itemId,
            posterPath = it.posterPath,
            title = it.title,
            genreIds = it.genreIds,
            voteAverage = it.voteAverage,
            overview = it.overview,
        )
    }
}