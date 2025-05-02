package com.example.shortplayer.data.repository

import android.util.Log
import com.example.shortplayer.data.local.dao.MovieDao
import com.example.shortplayer.data.local.dao.MovieDetailDao
import com.example.shortplayer.data.local.mapper.toDomain
import com.example.shortplayer.data.local.mapper.toEntity
import com.example.shortplayer.data.remote.api.MoviesApiService
import com.example.shortplayer.data.remote.dto.FavoriteRequestDto
import com.example.shortplayer.data.remote.mapper.toDomain as RemoteToDomain
import com.example.shortplayer.data.remote.mapper.toMovieDetail
import com.example.shortplayer.domain.model.Movie
import com.example.shortplayer.domain.model.MovieDetail
import com.example.shortplayer.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val apiService: MoviesApiService,
    private val movieDao: MovieDao,
    private val movieDetailDao: MovieDetailDao
) : MovieRepository {

    override suspend fun getTrendingMovies(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = apiService.getTrendingMovies()
            Log.d("Repo", "Trending remote count: ${response.results.size}")
            val movies = response.results.map { it.RemoteToDomain() }
            val entities = movies.map { it.toEntity("trending") }
            Log.d("Repo", "Trending entities: $entities")
            movieDao.insertMovies(entities)
            movies
        } catch (e: Exception) {
            val localTrending = movieDao.getMoviesByCategory("trending")
            Log.d("Repo", "Local trending count: ${localTrending.size} ${e.message}")
            return@withContext localTrending.map { it.toDomain() }        }
    }

    override suspend fun getNowPlayingMovies(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = apiService.getNowPlayingMovies()
            val movies = response.results.map { it.RemoteToDomain() }
            Log.d("Repo", "NowPlaying remote count: ${response.results.size}")
            movieDao.insertMovies(movies.map { it.toEntity("now_playing") })
            movies
        } catch (e: Exception) {
            movieDao.getMoviesByCategory("now_playing").map { it.toDomain() }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail = withContext(Dispatchers.IO) {
        return@withContext try {
            val detail = apiService.getMovieDetails(movieId).toMovieDetail()
            movieDetailDao.insertMovieDetail(detail.toEntity())
            detail
        } catch (e: Exception) {
            movieDetailDao.getMovieDetailById(movieId)?.toDomain()
                ?: throw e
        }
    }

    override suspend fun markAsFavorite(movieId: Int, isFav: Boolean): Boolean {
        val response = apiService.markAsFavorite(
            accountId = "21979262",
            request = FavoriteRequestDto(media_id = movieId, favorite = isFav)
        )
        return response.isSuccessful
    }

    override suspend fun getFavouriteMovies(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = apiService.getFavouriteMovies()
            response.results.map { it.RemoteToDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun isFavorite(movieId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val favourites = apiService.getFavouriteMovies().results
            return@withContext favourites.any { it.id == movieId }
        } catch (e: Exception) {
            return@withContext false
        }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return try {
            apiService.searchMovies(query = query).results.map { it.RemoteToDomain() }
        } catch (e: Exception) {
            emptyList() // or throw e if you want UI to handle errors
        }
    }

}
