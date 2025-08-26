package com.example.ratingroom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ratingroom.ui.utils.AppTopBar
import com.example.ratingroom.ui.utils.TopBarConfig
import com.example.ratingroom.ui.utils.EmptyActivityState
import com.example.ratingroom.ui.utils.ReviewCard

@Composable
fun ReviewsScreen(
    onBack: () -> Unit
) {
    var loading by remember { mutableStateOf(false) }   // cámbialo cuando conectes repo
    val reviews = remember {
        mutableStateListOf(
            // demo - reemplaza con tu repositorio real
            Triple("Inception", 5, "Una película increíble que te hace pensar. Los efectos visuales son espectaculares y la historia es muy original." ),
            Triple("The Matrix", 4, "Un clásico del cine de ciencia ficción. Revolucionó el género y sigue siendo relevante hoy en día."),
            Triple("Interstellar", 5, "Obra maestra: ciencia, emoción e imágenes se combinan perfectamente.")
        )
    }

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
            loading -> {
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
            reviews.isEmpty() -> {
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
                    items(reviews) { (title, rating, text) ->
                        ReviewCard(
                            title = title,
                            rating = rating,
                            excerpt = text,
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
    // Envuelve siempre en tu theme
    com.example.ratingroom.ui.theme.RatingRoomTheme {
        ReviewsScreen(onBack = {})
    }
}

