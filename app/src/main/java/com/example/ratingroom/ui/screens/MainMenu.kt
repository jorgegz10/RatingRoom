package com.example.ratingroom.ui.screens

import com.example.ratingroom.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource


data class Movie(
    val title: String,
    val year: String,
    val genre: String,
    val rating: String,
    val reviews: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onLogout: () -> Unit,
    onProfileClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF0D0B1F), Color(0xFF1C2D64))
    )

    val genres = listOf("Todos", "Romance", "Crime", "Action", "Sci-Fi", "Adventure")
    var selectedGenre by remember { mutableStateOf("Todos") }
    var filterExpanded by remember { mutableStateOf(false) }
    var profileMenuExpanded by remember { mutableStateOf(false) }

    val movies = listOf(
        Movie("Titanic", "1997", "Romance", "4.5", "3876 reseñas"),
        Movie("Pulp Fiction", "1994", "Crime", "4.9", "3421 reseñas"),
        Movie("The Dark Knight", "2008", "Action", "4.8", "2987 reseñas"),
        Movie("Avatar", "2009", "Sci-Fi", "4.4", "2987 reseñas"),
        Movie("Jurassic Park", "1993", "Adventure", "4.5", "2654 reseñas"),
        Movie("Goodfellas", "1990", "Crime", "4.8", "2234 reseñas")
    ).filter { selectedGenre == "Todos" || it.genre == selectedGenre }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ratingroom", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { /* Puedes dejar esto vacío si es solo logo */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.logoratingroom),
                            contentDescription = "Logo",
                            tint = Color.Unspecified // para que no lo pinte de blanco
                        )
                    }
                },
                actions = {
                    Box {
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .clickable { profileMenuExpanded = true },
                            contentAlignment = Alignment.Center
                        ) {}

                        DropdownMenu(
                            expanded = profileMenuExpanded,
                            onDismissRequest = { profileMenuExpanded = false }
                        ) {
                            DropdownMenuItem(text = { Text("Mi perfil") }, onClick = {
                                profileMenuExpanded = false
                                onProfileClick()
                            })
                            DropdownMenuItem(text = { Text("Cerrar sesión") }, onClick = {
                                profileMenuExpanded = false
                                onLogout()
                            })
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = gradient)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar películas, géneros, directores...") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box {
                Button(
                    onClick = { filterExpanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5A052))
                ) {
                    Text("Filtros", color = Color.Black)
                }

                DropdownMenu(
                    expanded = filterExpanded,
                    onDismissRequest = { filterExpanded = false }
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(genre) },
                            onClick = {
                                selectedGenre = genre
                                filterExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Películas Populares", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(movies) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Acción al tocar película */ },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                Text(
                                    movie.genre,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .background(Color.DarkGray, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp),
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(movie.title, fontWeight = FontWeight.Bold)
                            Text(movie.year, fontSize = 12.sp, color = Color.Gray)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(movie.rating, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(movie.reviews, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainMenuScreen() {
    MainMenuScreen(onLogout = {}, onProfileClick = {})
}
