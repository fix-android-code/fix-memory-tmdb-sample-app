package com.example.suki.movie.filter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_TABLE_NAME

@Entity(tableName = MOVIE_FILTER_TABLE_NAME)
data class MovieFilterCacheEntity(

    // autoGenerate is necessary
    @PrimaryKey(autoGenerate = true)
    val autoGenerateEntityId: Int = 0,

    @ColumnInfo(name = "id")
    val itemId: Int,
    val posterPath: String?,
    val title: String,
    val genreIds: List<Int>,
    val voteAverage: Double,
    val overview: String,

)