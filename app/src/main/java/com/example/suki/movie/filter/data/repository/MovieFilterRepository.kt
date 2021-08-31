package com.example.suki.movie.filter.data.repository

import androidx.paging.PagingData
import com.example.suki.movie.filter.model.MovieFilterModel
import kotlinx.coroutines.flow.Flow

@OptIn(androidx.paging.ExperimentalPagingApi::class)
interface MovieFilterRepository {

    fun getAllMovieFilterPaging(filter: String): Flow<PagingData<MovieFilterModel>>
}

