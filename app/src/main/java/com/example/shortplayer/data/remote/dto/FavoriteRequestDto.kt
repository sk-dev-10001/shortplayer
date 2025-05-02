package com.example.shortplayer.data.remote.dto

data class FavoriteRequestDto(
    val media_type: String = "movie",
    val media_id: Int,
    val favorite: Boolean
)
