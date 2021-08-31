package com.example.suki.movie.filter.di

import com.example.suki.common.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieFilterModule {

    @Provides
    @Singleton
    fun provideMovieFilterDao(
        database: AppDatabase
    ) = database.getMovieFilterDao()
}