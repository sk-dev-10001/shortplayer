package com.example.shortplayer.domain.repository

import com.example.shortplayer.domain.model.Movie
import com.example.shortplayer.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getTrendingMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun markAsFavorite(movieId: Int, isFav: Boolean): Boolean
    suspend fun getFavouriteMovies(): List<Movie>
    suspend fun isFavorite(movieId: Int): Boolean // <-- Add this line
    suspend fun searchMovies(query: String): List<Movie>

}
