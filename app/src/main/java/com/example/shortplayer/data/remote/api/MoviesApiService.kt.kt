package com.example.shortplayer.data.remote.api

import com.example.shortplayer.data.remote.dto.FavoriteRequestDto
import com.example.shortplayer.data.remote.dto.FavouriteMoviesResponseDto
import com.example.shortplayer.data.remote.dto.MovieDetailDto
import com.example.shortplayer.data.remote.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/popular")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Path("account_id") accountId: String,
        @Body request: FavoriteRequestDto
    ): Response<Unit>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavouriteMovies(
        @Path("account_id") accountId: Int = 21979262,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): FavouriteMoviesResponseDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseDto

}
