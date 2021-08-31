package com.example.suki.common.util

sealed class Resource<out T> {

    data class Success<T>(
        val data: T? = null,
        val source: DataSource

    ) : Resource<T>()

    data class Failure<T>(
        val data: T? = null,
        val failureData: FailureData
    ) : Resource<T>()

    data class Loading<T>(
        val data: T? = null,
        val source: DataSource
    ) : Resource<T>()

    object None : Resource<Nothing>()
}

sealed class DataSource {
    object CACHE : DataSource()
    object REMOTE : DataSource()
}

data class FailureData(val code: Int, val message: String? = null)
