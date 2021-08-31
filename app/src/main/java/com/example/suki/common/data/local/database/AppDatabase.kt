package com.example.suki.common.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.suki.movie.filter.data.local.dao.MovieFilterDao
import com.example.suki.movie.filter.data.local.dao.MovieFilterRemoteKeyDao
import com.example.suki.movie.filter.data.local.entity.MovieFilterCacheEntity
import com.example.suki.movie.filter.data.local.entity.MovieFilterRemoteKeyCacheEntity
import com.example.suki.movie.filter.data.local.typeconverter.MovieFilterTypeConverter

@Database(
    entities = [MovieFilterCacheEntity::class, MovieFilterRemoteKeyCacheEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(MovieFilterTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieFilterDao(): MovieFilterDao
    abstract fun getMovieFilterRemoteKeyDao(): MovieFilterRemoteKeyDao
}