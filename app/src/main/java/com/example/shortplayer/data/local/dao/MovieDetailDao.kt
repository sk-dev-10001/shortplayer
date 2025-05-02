package com.example.shortplayer.data.local.dao

import androidx.room.*
import com.example.shortplayer.data.local.entities.MovieDetailEntity

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movie: MovieDetailEntity)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetailById(movieId: Int): MovieDetailEntity?

    @Update
    suspend fun updateMovieDetail(movie: MovieDetailEntity)

}
