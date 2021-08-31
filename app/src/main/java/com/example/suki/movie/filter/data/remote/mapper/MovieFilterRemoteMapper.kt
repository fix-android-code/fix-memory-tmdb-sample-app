package com.example.suki.movie.filter.data.remote.mapper

import com.example.suki.movie.filter.data.remote.response.MovieFilterNetworkDto
import com.example.suki.movie.filter.model.MovieFilterModel

fun MovieFilterNetworkDto.mapMovieFilterNetworkDtoToMovieFilterModel(): MovieFilterModel {
    return MovieFilterModel(
        itemId = id,
        posterPath = posterPath,
        title = title,
        genreIds = genreIds,
        voteAverage = voteAverage,
        overview = overview,
    )
}

fun MovieFilterModel.mapMovieFilterModelToMovieFilterNetworkDto(): MovieFilterNetworkDto {
    return MovieFilterNetworkDto(
        id = itemId,
        title = title,
        posterPath = posterPath,
        genreIds = genreIds,
        voteAverage = voteAverage,
        overview = overview,
    )
}

fun List<MovieFilterNetworkDto>.mapMovieFilterNetworkDtoListToMovieFilterModelList(): List<MovieFilterModel> {
    return this.map {
        MovieFilterModel(
            itemId = it.id,
            title = it.title,
            posterPath = it.posterPath,
            genreIds = it.genreIds,
            voteAverage = it.voteAverage,
            overview = it.overview,
        )
    }
}

fun List<MovieFilterModel>.mapMovieFilterModelListToMovieFilterNetworkDtoList(): List<MovieFilterNetworkDto> {
    return this.map {
        MovieFilterNetworkDto(
            id = it.itemId,
            title = it.title,
            posterPath = it.posterPath,
            genreIds = it.genreIds,
            voteAverage = it.voteAverage,
            overview = it.overview,
        )
    }
}