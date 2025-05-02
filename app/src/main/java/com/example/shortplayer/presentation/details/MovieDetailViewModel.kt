package com.example.shortplayer.presentation.details

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortplayer.domain.model.MovieDetail
import com.example.shortplayer.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movie = mutableStateOf<MovieDetail?>(null)
    val movie: State<MovieDetail?> = _movie

    private val _isFavourite = mutableStateOf(false)
    val isFavourite: State<Boolean> = _isFavourite

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                _movie.value = repository.getMovieDetail(movieId)
                _isFavourite.value = repository.isFavorite(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                val newStatus = !_isFavourite.value
                val success = repository.markAsFavorite(movieId, newStatus)
                if (success) {
                    _isFavourite.value = newStatus
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun shareMovie(context: Context, movieId: Int, movieTitle: String) {
        val shareText = "Check out this movie: $movieTitle\n" +
                "https://shortplayer.com/movies/$movieId"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

}
