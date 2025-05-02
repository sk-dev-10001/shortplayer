package com.example.shortplayer.data.remote.mapper

import com.example.shortplayer.data.remote.dto.MovieDto
import com.example.shortplayer.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = "https://image.tmdb.org/t/p/w500$posterPath",
        overview = overview,
        releaseDate = releaseDate,
    )
}
