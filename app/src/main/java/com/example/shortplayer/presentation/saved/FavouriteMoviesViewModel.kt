package com.example.shortplayer.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortplayer.domain.model.Movie
import com.example.shortplayer.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _favouriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favouriteMovies: StateFlow<List<Movie>> = _favouriteMovies

    init {
        fetchFavourites()
    }

    private fun fetchFavourites() {
        viewModelScope.launch {
            _favouriteMovies.value = repository.getFavouriteMovies()
        }
    }
}
