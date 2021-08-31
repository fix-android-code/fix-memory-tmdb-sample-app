package com.example.suki.common.util.di

import com.example.suki.common.util.coroutine.DispatcherProvider
import com.example.suki.common.util.coroutine.DispatcherProviderImpl
import com.example.suki.common.util.networkstatus.NetworkStatusProvider
import com.example.suki.common.util.networkstatus.NetworkStatusProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class AppBinderModule {
    @Binds
    @Singleton
    abstract fun bindNetworkStatusProvider(networkStatusProviderImpl: NetworkStatusProviderImpl): NetworkStatusProvider

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(
        dispatcherProviderImpl: DispatcherProviderImpl
    ): DispatcherProvider
}
