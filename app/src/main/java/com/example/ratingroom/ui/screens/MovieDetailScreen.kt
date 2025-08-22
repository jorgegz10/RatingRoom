package com.example.ratingroom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.repository.MovieRepository
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.GradientBackground
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.clickable


@Composable
fun MovieDetailScreen(
    movie: Movie,
    modifier: Modifier = Modifier,
    onShowMore: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val reviews = remember { MovieRepository.getReviewsForMovie(movie.id) }

    GradientBackground {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen de la película (placeholder sobre surfaceVariant)
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = cs.surfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Imagen de ${movie.title}",
                            color = cs.onSurfaceVariant
                        )
                    }
                }
            }

            // Información básica
            item {
                Column {
                    Text(
                        text = movie.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = cs.onPrimary
                    )
                    Text(
                        text = "${movie.year} • ${movie.duration} • ${movie.genre}",
                        fontSize = 16.sp,
                        color = cs.onPrimary.copy(alpha = 0.85f)
                    )
                    Text(
                        text = "Dirigida por ${movie.director}",
                        fontSize = 14.sp,
                        color = cs.onPrimary.copy(alpha = 0.75f)
                    )
                }
            }

            // Rating
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = cs.tertiary, // antes: #FFD700
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = movie.rating.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = cs.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(${movie.reviews} reseñas)",
                        fontSize = 16.sp,
                        color = cs.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }

            // Descripción
            item {
                Column {
                    Text(
                        text = "Sinopsis",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = cs.onPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.description,
                        fontSize = 14.sp,
                        color = cs.onPrimary,
                        lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Ver más",
                        color = cs.onPrimary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onShowMore() }
                    )
                }
            }

            // Reseñas (título)
            item {
                Text(
                    text = "Reseñas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = cs.onPrimary
                )
            }

            // Cards de reseñas
            items(reviews) { review ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cs.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Usuario ${review.userId}",
                                fontWeight = FontWeight.Bold,
                                color = cs.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = cs.tertiary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = review.rating.toString(),
                                    color = cs.onSurface,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = review.comment,
                            color = cs.onSurface,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = review.date,
                            color = cs.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMovieDetailScreen() {
    RatingRoomTheme {
        // Ejemplo simple de Movie para el preview
        val sample = Movie(
            id = 1,
            title = "Interestelar",
            year = "2014",
            duration = "2h 49m",
            genre = "Sci-Fi",
            director = "Christopher Nolan",
            rating = 4.7,
            reviews = 1284,
            description = "Un equipo de exploradores viaja a través de un agujero de gusano en el espacio para asegurar el futuro de la humanidad."
        )
    }
}

