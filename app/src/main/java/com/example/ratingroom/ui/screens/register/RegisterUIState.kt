package com.example.ratingroom.ui.screens.register

data class RegisterUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthdate: String = "",
    val genre: String = "",
    val fullName: String = "",
    val favoriteGenre: String = "",
    val birthYear: String = "",
    val acceptTerms: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)