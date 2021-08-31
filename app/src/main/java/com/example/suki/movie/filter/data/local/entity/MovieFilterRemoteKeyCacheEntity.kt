package com.example.suki.movie.filter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_REMOTE_KEY_LABEL

@Entity(tableName = MOVIE_FILTER_REMOTE_KEY_LABEL)
data class MovieFilterRemoteKeyCacheEntity(

    @PrimaryKey
    val label: String,
    val nextKey: Int?
)