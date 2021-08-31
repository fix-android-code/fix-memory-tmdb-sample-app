package com.example.suki.movie.filter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.suki.movie.filter.data.local.entity.MovieFilterRemoteKeyCacheEntity
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_REMOTE_KEY_LABEL

@Dao
interface MovieFilterRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceMovieFilterRemoteKey(remoteKeyEntity: MovieFilterRemoteKeyCacheEntity)

    @Query("SELECT * FROM $MOVIE_FILTER_REMOTE_KEY_LABEL WHERE label = :label")
    suspend fun getLabelMovieFilterRemoteKey(label: String): MovieFilterRemoteKeyCacheEntity?

    @Query("DELETE FROM $MOVIE_FILTER_REMOTE_KEY_LABEL WHERE label = :label")
    suspend fun deleteByLabelMovieFilterRemoteKey(label: String)

}