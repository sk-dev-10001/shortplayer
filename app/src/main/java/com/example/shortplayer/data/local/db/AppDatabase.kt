package com.example.shortplayer.data.local.db



import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shortplayer.data.local.dao.MovieDao
import com.example.shortplayer.data.local.dao.MovieDetailDao
import com.example.shortplayer.data.local.entities.MovieDetailEntity
import com.example.shortplayer.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
}

