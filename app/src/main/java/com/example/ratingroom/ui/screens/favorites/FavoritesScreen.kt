package com.example.ratingroom.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.AppTopBar
import com.example.ratingroom.ui.utils.TopBarConfig
import com.example.ratingroom.ui.utils.MovieCard
import com.example.ratingroom.ui.utils.EmptyActivityState

@Composable
fun FavoritesScreen(
    onBack: () -> Unit = {},
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    FavoritesScreenContent(
        uiState = uiState,
        onBack = onBack,
        onMovieClick = onMovieClick,
        onRemoveFromFavorites = viewModel::removeFromFavorites,
        modifier = modifier
    )
}

@Composable
fun FavoritesScreenContent(
    uiState: FavoritesUIState,
    onBack: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onRemoveFromFavorites: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopBar(
                TopBarConfig(
                    title = "Mis Favoritos",
                    showBackButton = true,
                    onBackClick = onBack
                )
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.favoriteMovies.isEmpty() && !uiState.isLoading -> {
                EmptyActivityState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
            else -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.favoriteMovies) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            onFavoriteClick = { onRemoveFromFavorites(movie) },
                            isFavorite = true
                        )
                    }
                }
            }
        }
        
        uiState.errorMessage?.let { error ->
            LaunchedEffect(error) {
                // TODO: Mostrar Snackbar con el error
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoritesScreenPreview() {
    RatingRoomTheme {
        FavoritesScreenContent(
            uiState = FavoritesUIState(
                favoriteMovies = emptyList(),
                isLoading = false
            ),
            onBack = {},
            onMovieClick = {},
            onRemoveFromFavorites = {}
        )
    }
}