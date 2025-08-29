package com.example.ratingroom.ui.screens.favorites

import com.example.ratingroom.data.models.Movie

data class FavoritesUIState(
    val favoriteMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)