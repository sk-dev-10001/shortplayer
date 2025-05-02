package com.example.shortplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shortplayer.presentation.details.MovieDetailScreen
import com.example.shortplayer.presentation.home.HomeScreen
import com.example.shortplayer.presentation.saved.FavouriteMoviesScreen
import com.example.shortplayer.presentation.theme.ShortPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShortPlayerTheme {
                val navController = rememberNavController()

                // Wrapping everything in a Scaffold
                Scaffold(
                    topBar = {
                        // TopAppBar can go here
                        // For example, you can add a title, an icon, etc.
                    },
                    bottomBar = {
                        // Bottom Navigation can go here, if required
                    }
                ) { innerPadding ->
                    // NavHost is placed within the Scaffold content section
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding) // Adjusting padding
                    ) {
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable("movieDetail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: return@composable
                            MovieDetailScreen(movieId)
                        }
                        composable("favourite") {
                            FavouriteMoviesScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate("movieDetail/$movieId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
