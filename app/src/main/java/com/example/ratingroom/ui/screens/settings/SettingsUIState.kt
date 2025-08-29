package com.example.ratingroom.ui.screens.settings

data class SettingsUIState(
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val selectedLanguage: String = "es",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)