package com.example.ratingroom.ui.screens.main

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.ui.utils.*
import com.example.ratingroom.data.repository.MovieRepository
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.ui.theme.RatingRoomTheme

@Composable
fun MainMenuScreen(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainMenuViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    MainMenuScreenContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onGenreSelected = viewModel::onGenreSelected,
        onFilterExpandedChange = viewModel::onFilterExpandedChange,
        onMovieClick = onMovieClick,
        modifier = modifier
    )
}

@Composable
fun MainMenuScreenContent(
    uiState: MainMenuUIState,
    onSearchQueryChange: (String) -> Unit,
    onGenreSelected: (String) -> Unit,
    onFilterExpandedChange: (Boolean) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Obtener datos del repositorio
    val genres = MovieRepository.getGenres()

    // Filtrar películas basado en género y búsqueda
    val filteredMovies = remember(uiState.selectedGenre, uiState.searchQuery) {
        val moviesByGenre = MovieRepository.getMoviesByGenre(uiState.selectedGenre)
        if (uiState.searchQuery.isBlank()) {
            moviesByGenre
        } else {
            MovieRepository.searchMovies(uiState.searchQuery).filter { movie ->
                uiState.selectedGenre == "Todos" || movie.genre == uiState.selectedGenre
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
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = stringResource(id = R.string.main_menu_search_placeholder)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros
            FilterDropdown(
                expanded = uiState.filterExpanded,
                onExpandedChange = onFilterExpandedChange,
                selectedGenre = uiState.selectedGenre,
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
        MainMenuScreenContent(
            uiState = MainMenuUIState(
                searchQuery = "",
                selectedGenre = "Todos",
                filterExpanded = false
            ),
            onSearchQueryChange = {},
            onGenreSelected = {},
            onFilterExpandedChange = {},
            onMovieClick = {}
        )
    }
}
