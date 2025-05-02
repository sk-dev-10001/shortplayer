package com.example.shortplayer.data.remote.dto

data class FavouriteMoviesResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)
