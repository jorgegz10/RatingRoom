package com.example.ratingroom.ui.screens.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratingroom.ui.utils.AppTopBar
import com.example.ratingroom.ui.utils.TopBarConfig
import com.example.ratingroom.ui.utils.EmptyActivityState
import com.example.ratingroom.ui.utils.ReviewCard
import com.example.ratingroom.ui.theme.RatingRoomTheme

@Composable
fun ReviewsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    ReviewsScreenContent(
        uiState = uiState,
        onBack = onBack,
        onEditReview = viewModel::editReview,
        onDeleteReview = viewModel::deleteReview,
        onClearError = viewModel::clearError,
        modifier = modifier
    )
}

@Composable
fun ReviewsScreenContent(
    uiState: ReviewsUIState,
    onBack: () -> Unit,
    onEditReview: (ReviewItem) -> Unit,
    onDeleteReview: (ReviewItem) -> Unit,
    onClearError: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            AppTopBar(
                TopBarConfig(
                    title = "Mis Reseñas",
                    showBackButton = true,
                    onBackClick = onBack
                )
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Column(Modifier.padding(padding).padding(16.dp)) {
                    repeat(3) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(96.dp)
                                .padding(bottom = 12.dp),
                            tonalElevation = 1.dp
                        ) {}
                    }
                }
            }
            uiState.reviews.isEmpty() && !uiState.isLoading -> {
                EmptyActivityState(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.reviews) { review ->
                        ReviewCard(
                            title = review.movieTitle,
                            rating = review.rating,
                            excerpt = review.comment,
                            timeAgo = "Hace 3 días"
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewsScreenPreview() {
    RatingRoomTheme {
        ReviewsScreenContent(
            uiState = ReviewsUIState(
                reviews = listOf(
                    ReviewItem(
                        movieTitle = "Inception",
                        rating = 5,
                        comment = "Una película increíble que te hace pensar."
                    ),
                    ReviewItem(
                        movieTitle = "The Matrix",
                        rating = 4,
                        comment = "Un clásico del cine de ciencia ficción."
                    )
                ),
                isLoading = false
            ),
            onBack = {},
            onEditReview = {},
            onDeleteReview = {},
            onClearError = {}
        )
    }
}

