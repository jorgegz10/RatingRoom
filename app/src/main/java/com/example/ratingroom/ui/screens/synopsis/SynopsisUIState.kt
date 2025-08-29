package com.example.ratingroom.ui.screens.synopsis

import com.example.ratingroom.data.models.Movie

data class CastMember(
    val name: String,
    val character: String,
    val isMain: Boolean = false
)

data class SynopsisUIState(
    val movie: Movie? = null,
    val cast: List<CastMember> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)