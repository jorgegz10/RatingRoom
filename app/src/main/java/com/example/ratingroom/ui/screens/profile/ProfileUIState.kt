package com.example.ratingroom.ui.screens.profile

data class ProfileData(
    val name: String,
    val email: String,
    val memberSince: String,
    val favoriteGenre: String,
    val reviewsCount: Int,
    val averageRating: Double
)

data class ProfileUIState(
    val profileData: ProfileData? = null,
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)