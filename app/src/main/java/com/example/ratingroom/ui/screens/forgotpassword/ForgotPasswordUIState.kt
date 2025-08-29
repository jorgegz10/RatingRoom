package com.example.ratingroom.ui.screens.forgotpassword

data class ForgotPasswordUIState(
    val email: String = "",
    val showHowItWorks: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)