package com.example.suki.movie.filter.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.suki.common.data.local.database.AppDatabase
import com.example.suki.common.data.remote.api.AppApi
import com.example.suki.movie.filter.data.local.mapper.mapMovieFilterCacheEntityToMovieFilterModel
import com.example.suki.movie.filter.data.paging.MovieFilterPagingRemoteMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class MovieFilterRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val appApi: AppApi,
    @ApplicationContext private val context: Context
) : MovieFilterRepository {


    override fun getAllMovieFilterPaging(filter: String) = Pager(
        config = PagingConfig(
            pageSize = 4
        ),
        remoteMediator = MovieFilterPagingRemoteMediator(
            api = appApi,
            database = database,
            filter = filter
        ),
        pagingSourceFactory = { database.getMovieFilterDao().getAllMovieFilter() }
    ).flow.map { pagingData ->
        pagingData.map {
            it.mapMovieFilterCacheEntityToMovieFilterModel()
        }
    }
}

