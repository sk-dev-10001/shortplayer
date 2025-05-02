package com.example.shortplayer.data.local.mapper


import com.example.shortplayer.data.local.entities.MovieEntity
import com.example.shortplayer.domain.model.Movie

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
)


fun Movie.toEntity(category: String): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,  // Mapping releaseDate,
    category = category
)


