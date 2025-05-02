package com.example.shortplayer.data.remote.mapper

import com.example.shortplayer.data.remote.dto.GenreDto
import com.example.shortplayer.data.remote.dto.MovieDetailDto
import com.example.shortplayer.domain.model.Genre
import com.example.shortplayer.domain.model.MovieDetail

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        tagline = tagline,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        genres = genres.map { it.toGenre() }
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}
