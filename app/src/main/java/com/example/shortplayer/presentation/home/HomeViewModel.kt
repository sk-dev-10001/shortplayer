package com.example.shortplayer.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortplayer.domain.model.Movie
import com.example.shortplayer.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var trendingMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var nowPlayingMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var searchQuery by mutableStateOf("")
        private set

    var searchResults by mutableStateOf<List<Movie>>(emptyList())
        private set

    private var searchJob: Job? = null

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            isLoading = true
            trendingMovies = repository.getTrendingMovies()
            nowPlayingMovies = repository.getNowPlayingMovies()
            Log.d("HomeViewModel", "Trending: ${trendingMovies.size}, NowPlaying: ${nowPlayingMovies.size}")
            isLoading = false
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // debounce delay
            if (query.isNotBlank()) {
                try {
                    searchResults = repository.searchMovies(query)
                } catch (e: Exception) {
                    searchResults = emptyList()
                }
            } else {
                searchResults = emptyList()
            }
        }
    }
}

