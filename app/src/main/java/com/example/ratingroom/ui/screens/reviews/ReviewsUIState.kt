package com.example.ratingroom.ui.screens.reviews

data class ReviewItem(
    val movieTitle: String,
    val rating: Int,
    val comment: String
)

data class ReviewsUIState(
    val reviews: List<ReviewItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)