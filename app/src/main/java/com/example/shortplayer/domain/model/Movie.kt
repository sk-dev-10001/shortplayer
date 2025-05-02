package com.example.shortplayer.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String?,
)

