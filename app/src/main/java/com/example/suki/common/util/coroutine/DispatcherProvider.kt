package com.example.suki.common.util.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}
