package com.example.suki.movie.filter.ui.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieFilterPreferencesDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("movie_filter_datastore_preferences")

    val movieFilterPreferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "exception: IOException: ${exception}")
                emit(emptyPreferences())
            } else {
                Timber.d("exception: $exception")
            }
        }
        .map { preferences ->
            FilterType.valueOf(
                preferences[PreferencesKeys.FILTER_TYPE] ?: FilterType.POPULAR_MOVIE_FILTER.name
            )
        }

    suspend fun updateFilterType(filterOrder: FilterType) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FILTER_TYPE] = filterOrder.name
        }
    }

    private object PreferencesKeys {
        val FILTER_TYPE = stringPreferencesKey("filter_type")
    }
}

enum class FilterType {
    POPULAR_MOVIE_FILTER, UPCOMING_MOVIE_FILTER, TOP_RATED_MOVIE_FILTER
}