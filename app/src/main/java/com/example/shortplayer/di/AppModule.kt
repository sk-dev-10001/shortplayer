package com.example.shortplayer.di

import android.content.Context
import androidx.room.Room
import com.example.shortplayer.data.local.dao.MovieDao
import com.example.shortplayer.data.local.dao.MovieDetailDao
import com.example.shortplayer.data.local.db.AppDatabase
import com.example.shortplayer.data.remote.api.MoviesApiService
import com.example.shortplayer.data.repository.MovieRepositoryImpl
import com.example.shortplayer.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Provides
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ")
            .addHeader("accept", "application/json")
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, headerInterceptor: Interceptor): MoviesApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().addInterceptor(headerInterceptor).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)
    }

    // ✅ ROOM DATABASE
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    // ✅ Add this missing provider
    @Provides
    @Singleton
    fun provideMovieDetailDao(database: AppDatabase): MovieDetailDao {
        return database.movieDetailDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MoviesApiService,
        movieDao: MovieDao,
        movieDetailDao: MovieDetailDao
    ): MovieRepository = MovieRepositoryImpl(api, movieDao,movieDetailDao)
}
