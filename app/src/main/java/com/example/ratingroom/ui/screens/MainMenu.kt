package com.example.ratingroom.ui.screens

import com.example.ratingroom.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ratingroom.ui.utils.*
import com.example.ratingroom.data.repository.MovieRepository
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.ui.theme.RatingRoomTheme

@Composable
fun MainMenuScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedGenre: String,
    onGenreSelected: (String) -> Unit,
    filterExpanded: Boolean,
    onFilterExpandedChange: (Boolean) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Obtener datos del repositorio
    val genres = MovieRepository.getGenres()

    // Filtrar películas basado en género y búsqueda
    val filteredMovies = remember(selectedGenre, searchQuery) {
        val moviesByGenre = MovieRepository.getMoviesByGenre(selectedGenre)
        if (searchQuery.isBlank()) {
            moviesByGenre
        } else {
            MovieRepository.searchMovies(searchQuery).filter { movie ->
                selectedGenre == "Todos" || movie.genre == selectedGenre
            }
        }
    }

    val cs = MaterialTheme.colorScheme

    GradientBackground { // ✅ fondo azul degradado
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Barra de búsqueda
            SearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = stringResource(id = R.string.main_menu_search_placeholder)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros
            FilterDropdown(
                expanded = filterExpanded,
                onExpandedChange = onFilterExpandedChange,
                selectedGenre = selectedGenre,
                genres = genres,
                onGenreSelected = onGenreSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                stringResource(id = R.string.main_menu_popular_movies),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = cs.onPrimary // antes: Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Grid de películas con LazyVerticalGrid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(filteredMovies) { movie ->
                    MovieCard(
                        movie = movie,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainMenuScreen() {
    RatingRoomTheme {
        MainMenuScreen(
            searchQuery = "",
            onSearchQueryChange = {},
            selectedGenre = "Todos",
            onGenreSelected = {},
            filterExpanded = false,
            onFilterExpandedChange = {},
            onMovieClick = {}
        )
    }
}
