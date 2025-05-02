package com.example.shortplayer.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shortplayer.presentation.home.components.MovieItem


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trending = viewModel.trendingMovies
    val nowPlaying = viewModel.nowPlayingMovies
    val loading = viewModel.isLoading
    val searchQuery = viewModel.searchQuery
    val searchResults = viewModel.searchResults

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = Color.White,
                title = { Text("Short Player") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("favourite")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favourite Movies"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp)) {

            // 🔍 Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                label = { Text("Search Movies") }
            )

            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (searchQuery.isNotBlank()) {
                if (searchResults.isEmpty()) {
                    Text("No results found", style = MaterialTheme.typography.body1)
                } else {
                    Text("Search Results", style = MaterialTheme.typography.h6)
                    LazyColumn {
                        items(searchResults) { movie ->
                            MovieItem(movie = movie) {
                                navController.navigate("movieDetail/${movie.id}")
                            }
                        }
                    }
                }
            } else {
                // Trending Section
                Text("Trending", style = MaterialTheme.typography.h6)
                LazyRow {
                    items(trending) { movie ->
                        MovieItem(movie) {
                            navController.navigate("movieDetail/${movie.id}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Now Playing Section
                Text("Now Playing", style = MaterialTheme.typography.h6)
                LazyRow {
                    items(nowPlaying) { movie ->
                        MovieItem(movie) {
                            navController.navigate("movieDetail/${movie.id}")
                        }
                    }
                }
            }
        }
    }
}
