package com.example.ratingroom.ui.screens.moviedetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratingroom.data.models.Review
import com.example.ratingroom.ui.utils.MoviePoster

@Composable
fun MovieDetailRoute(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) { viewModel.loadMovieDetail(movieId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MovieDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onClearError = { viewModel.clearError() }
    )
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailUIState,
    onBack: () -> Unit = {},
    onClearError: () -> Unit = {}
) {
    val movie = uiState.movie
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        topBar = {
            // TopBar estable
            Surface(
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = movie?.title ?: "Detalle",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    action = { TextButton(onClick = onClearError) { Text("OK") } }
                ) { Text(data.visuals.message) }
            }
        }
    ) { inner ->
        when {
            uiState.isLoading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(inner),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            movie != null -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(inner)
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // Poster 2:3
                        MoviePoster(
                            imageRes = movie.imageRes,
                            movieTitle = movie.title,
                            width = 260.dp,
                            height = 390.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f / 3f)
                        )
                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "${movie.year} • ${movie.duration}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = movie.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "Reseñas",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(8.dp))
                    }

                    items(uiState.reviews) { review ->
                        ReviewItem(review = review)
                        Spacer(Modifier.height(12.dp))
                    }

                    item { Spacer(Modifier.height(24.dp)) }
                }
            }

            else -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(inner),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontró la película",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewItem(review: Review) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = "⭐ ${review.rating}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = review.comment,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = review.date,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
