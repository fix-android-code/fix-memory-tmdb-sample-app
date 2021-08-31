package com.example.suki.common.util.coroutine

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override fun io() = Dispatchers.IO
    override fun ui() = Dispatchers.Main
    override fun default() = Dispatchers.Default
}
