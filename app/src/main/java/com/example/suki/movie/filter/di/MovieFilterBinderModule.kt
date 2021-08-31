package com.example.suki.movie.filter.di

import com.example.suki.movie.filter.data.repository.MovieFilterRepository
import com.example.suki.movie.filter.data.repository.MovieFilterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class MovieFilterBinderModule {

    @Binds
    @Singleton
    abstract fun bindMovieFilterRepository(
        movieFilterRepositoryImpl: MovieFilterRepositoryImpl
    ): MovieFilterRepository
}