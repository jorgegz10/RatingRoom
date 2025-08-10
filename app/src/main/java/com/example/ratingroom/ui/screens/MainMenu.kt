package com.example.ratingroom.ui.screens

import com.example.ratingroom.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ratingroom.ui.utils.*

data class Movie(
    val title: String,
    val year: String,
    val genre: String,
    val rating: String,
    val reviews: String
)

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier
) {
    // String resources
    val genres = listOf(
        stringResource(id = R.string.genre_all),
        stringResource(id = R.string.genre_romance),
        stringResource(id = R.string.genre_crime),
        stringResource(id = R.string.genre_action),
        stringResource(id = R.string.genre_scifi),
        stringResource(id = R.string.genre_adventure)
    )
    
    val genreAll = stringResource(id = R.string.genre_all)
    var selectedGenre by remember { mutableStateOf(genreAll) }
    var filterExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val allMovies = listOf(
        Movie(
            stringResource(id = R.string.movie_titanic),
            stringResource(id = R.string.year_1997),
            stringResource(id = R.string.genre_romance),
            stringResource(id = R.string.rating_4_5),
            stringResource(id = R.string.reviews_3876)
        ),
        Movie(
            stringResource(id = R.string.movie_pulp_fiction),
            stringResource(id = R.string.year_1994),
            stringResource(id = R.string.genre_crime),
            stringResource(id = R.string.rating_4_9),
            stringResource(id = R.string.reviews_3421)
        ),
        Movie(
            stringResource(id = R.string.movie_dark_knight),
            stringResource(id = R.string.year_2008),
            stringResource(id = R.string.genre_action),
            stringResource(id = R.string.rating_4_8),
            stringResource(id = R.string.reviews_2987)
        ),
        Movie(
            stringResource(id = R.string.movie_avatar),
            stringResource(id = R.string.year_2009),
            stringResource(id = R.string.genre_scifi),
            stringResource(id = R.string.rating_4_4),
            stringResource(id = R.string.reviews_2987)
        ),
        Movie(
            stringResource(id = R.string.movie_jurassic_park),
            stringResource(id = R.string.year_1993),
            stringResource(id = R.string.genre_adventure),
            stringResource(id = R.string.rating_4_5),
            stringResource(id = R.string.reviews_2654)
        ),
        Movie(
            stringResource(id = R.string.movie_goodfellas),
            stringResource(id = R.string.year_1990),
            stringResource(id = R.string.genre_crime),
            stringResource(id = R.string.rating_4_8),
            stringResource(id = R.string.reviews_2234)
        )
    )
    
    val movies = allMovies.filter { selectedGenre == genreAll || it.genre == selectedGenre }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            placeholder = stringResource(id = R.string.main_menu_search_placeholder)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filtros
        FilterDropdown(
            expanded = filterExpanded,
            onExpandedChange = { filterExpanded = it },
            selectedGenre = selectedGenre,
            genres = genres,
            onGenreSelected = { selectedGenre = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            stringResource(id = R.string.main_menu_popular_movies),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Grid de películas
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(movies) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { /* Acción al tocar película */ }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainMenuScreen() {
    MainMenuScreen()
}
