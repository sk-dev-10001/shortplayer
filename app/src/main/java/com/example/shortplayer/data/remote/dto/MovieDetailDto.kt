package com.example.shortplayer.data.remote.dto

import com.example.shortplayer.domain.model.Genre
import com.example.shortplayer.domain.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    val tagline: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)
