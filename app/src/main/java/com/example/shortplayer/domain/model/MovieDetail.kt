package com.example.shortplayer.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val tagline: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)
