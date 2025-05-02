package com.example.shortplayer.presentation.saved


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.shortplayer.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteMoviesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: FavouriteMoviesViewModel = hiltViewModel()
) {
    val movies by viewModel.favouriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favourite Movies") })
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(movies) { movie ->
                MovieItem(movie, onMovieClick)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(movie.id) }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w200${movie.posterPath}",
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            Text(movie.overview.take(100), style = MaterialTheme.typography.bodySmall)
        }
    }
}
