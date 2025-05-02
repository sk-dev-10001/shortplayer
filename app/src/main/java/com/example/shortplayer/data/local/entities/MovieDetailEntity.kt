package com.example.shortplayer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val genres: String  // Store genres as a JSON string
)
