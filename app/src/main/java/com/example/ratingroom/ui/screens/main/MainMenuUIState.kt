package com.example.ratingroom.ui.screens.main

data class MainMenuUIState(
    val searchQuery: String = "",
    val selectedGenre: String = "Todos",
    val filterExpanded: Boolean = false,
    val isLoading: Boolean = false
)