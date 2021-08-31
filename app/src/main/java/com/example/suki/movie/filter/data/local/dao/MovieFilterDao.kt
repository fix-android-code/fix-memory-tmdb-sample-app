package com.example.suki.movie.filter.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.suki.movie.filter.data.local.entity.MovieFilterCacheEntity
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_TABLE_NAME

@Dao
interface MovieFilterDao {

    @Query("SELECT * FROM $MOVIE_FILTER_TABLE_NAME")
    fun getAllMovieFilter(): PagingSource<Int, MovieFilterCacheEntity>

    @Query("DELETE FROM $MOVIE_FILTER_TABLE_NAME")
    suspend fun deleteAllMovieFilter()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceMovieFilter(obj: MovieFilterCacheEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAllMovieFilter(obj: List<MovieFilterCacheEntity>)

    @Delete
    suspend fun deleteMovieFilter(obj: MovieFilterCacheEntity)

    @Query("SELECT COUNT(autoGenerateEntityId) FROM $MOVIE_FILTER_TABLE_NAME")
    suspend fun getCountMovieFilter(): Int
}

