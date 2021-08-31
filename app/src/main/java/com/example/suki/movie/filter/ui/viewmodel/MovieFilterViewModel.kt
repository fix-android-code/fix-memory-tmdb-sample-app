package com.example.suki.movie.filter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.suki.movie.filter.data.repository.MovieFilterRepository
import com.example.suki.movie.filter.ui.datastore.FilterType
import com.example.suki.movie.filter.ui.datastore.MovieFilterPreferencesDataStore
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_POPULAR_CONSTANT
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_TOP_RATED_CONSTANT
import com.example.suki.movie.filter.util.MovieFilterConstant.MOVIE_FILTER_UPCOMING_CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFilterViewModel @Inject constructor(
    movieFilterRepository: MovieFilterRepository,
    private val movieFilterPreferencesDataStore: MovieFilterPreferencesDataStore

) : ViewModel() {

    private val preferencesFlow = movieFilterPreferencesDataStore.movieFilterPreferencesFlow

    fun onFilterOrderSelected(filterType: FilterType) = viewModelScope.launch {
        movieFilterPreferencesDataStore.updateFilterType(filterType)
    }

    val filterType get() = preferencesFlow.asLiveData()

    val movieFilterFlowPaging = filterType.switchMap {

        val filter = when (it) {
            FilterType.POPULAR_MOVIE_FILTER -> {
                MOVIE_FILTER_POPULAR_CONSTANT
            }
            FilterType.UPCOMING_MOVIE_FILTER -> {
                MOVIE_FILTER_UPCOMING_CONSTANT
            }
            FilterType.TOP_RATED_MOVIE_FILTER -> {
                MOVIE_FILTER_TOP_RATED_CONSTANT
            }
        }
        movieFilterRepository.getAllMovieFilterPaging(filter).cachedIn(viewModelScope)
            .asLiveData()
    }
}