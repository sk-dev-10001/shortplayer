package com.example.shortplayer.data.local.mapper


import com.example.shortplayer.data.local.entities.MovieDetailEntity
import com.example.shortplayer.domain.model.MovieDetail
import com.example.shortplayer.domain.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun MovieDetailEntity.toDomain(): MovieDetail {
    val genreListType = object : TypeToken<List<Genre>>() {}.type
    val genres: List<Genre> = Gson().fromJson(this.genres, genreListType)  // Convert genres JSON string to List<Genre>

    return MovieDetail(
        id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        genres = genres  // Assign the converted genres list
    )
}


fun MovieDetail.toEntity(): MovieDetailEntity {
    val genresJson = Gson().toJson(genres)  // Convert genres list to JSON string

    return MovieDetailEntity(
        id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        genres = genresJson  // Store genres as JSON string
    )
}

