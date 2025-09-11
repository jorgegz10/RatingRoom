package com.example.ratingroom.ui.screens.profile

data class EditProfileUIState(
    val displayName: String = "",
    val email: String = "",
    val biography: String = "",
    val location: String = "",
    val favoriteGenre: String = "Género Favorito",
    val birthdate: String = "mm / dd / yyyy",
    val website: String = "",
    val profileImageUri: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)