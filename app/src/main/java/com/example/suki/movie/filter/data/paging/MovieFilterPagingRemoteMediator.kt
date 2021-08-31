package com.example.suki.movie.filter.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.suki.common.data.local.database.AppDatabase
import com.example.suki.common.data.remote.api.AppApi
import com.example.suki.movie.filter.data.local.entity.MovieFilterCacheEntity
import com.example.suki.movie.filter.data.local.entity.MovieFilterRemoteKeyCacheEntity
import com.example.suki.movie.filter.data.local.mapper.mapMovieFilterModelToMovieFilterCacheEntity
import com.example.suki.movie.filter.data.remote.mapper.mapMovieFilterNetworkDtoToMovieFilterModel
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_REMOTE_KEY_LABEL
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MovieFilterPagingRemoteMediator(
    private val api: AppApi,
    private val database: AppDatabase,
    private val filter: String
) : RemoteMediator<Int, MovieFilterCacheEntity>() {

    private val getMovieFilterDao = database.getMovieFilterDao()
    private val getMovieFilterRemoteKeyDao = database.getMovieFilterRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieFilterCacheEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> STARTING_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey =
                        getMovieFilterRemoteKeyDao.getLabelMovieFilterRemoteKey(MOVIE_FILTER_REMOTE_KEY_LABEL)
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.nextKey
                }
            }
            Timber.d("loadKey: ${loadKey}")
            val response = loadKey?.let {
                api.getMovieFilterApi(
                    filter = filter,
                    page = it
                )
            } ?: return MediatorResult.Error(IllegalStateException("response is null"))

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    getMovieFilterRemoteKeyDao.deleteByLabelMovieFilterRemoteKey(MOVIE_FILTER_REMOTE_KEY_LABEL)
                    getMovieFilterDao.deleteAllMovieFilter()
                }
                getMovieFilterRemoteKeyDao.insertOrReplaceMovieFilterRemoteKey(
                    MovieFilterRemoteKeyCacheEntity(
                        MOVIE_FILTER_REMOTE_KEY_LABEL,
                        response.page + 1
                    )
                )
                getMovieFilterDao.insertOrReplaceAllMovieFilter(
                    response.results.map {
                        it.mapMovieFilterNetworkDtoToMovieFilterModel().mapMovieFilterModelToMovieFilterCacheEntity()
                    }
                )
            }

            return MediatorResult.Success(endOfPaginationReached = response.page >= response.totalPages)
        } catch (ioEx: IOException) {

            Timber.d("load: ioEx: $ioEx")
            return if (loadType == LoadType.REFRESH && database.getMovieFilterDao()
                    .getCountMovieFilter() > 0
            ) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(ioEx)
            }

        } catch (httpEx: HttpException) {
            Timber.d("load: ioEx: $httpEx")
            return if (loadType == LoadType.REFRESH && database.getMovieFilterDao()
                    .getCountMovieFilter() > 0
            ) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(httpEx)
            }
        }
    }
}