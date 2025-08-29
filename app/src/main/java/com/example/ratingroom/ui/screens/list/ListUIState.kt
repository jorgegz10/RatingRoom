package com.example.ratingroom.ui.screens.list

import com.example.ratingroom.data.models.Movie

data class ListUIState(
    val selectedTab: Int = 0,
    val watchLaterMovies: List<Movie> = emptyList(),
    val favoriteMovies: List<Movie> = emptyList(),
    val watchedMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)