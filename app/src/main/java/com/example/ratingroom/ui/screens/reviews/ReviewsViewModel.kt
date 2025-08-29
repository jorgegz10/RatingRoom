package com.example.ratingroom.ui.screens.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ReviewsViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(ReviewsUIState())
    val uiState: StateFlow<ReviewsUIState> = _uiState.asStateFlow()
    
    init {
        loadReviews()
    }
    
    private fun loadReviews() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                delay(1000) // Simular carga
                
                val reviews = listOf(
                    ReviewItem(
                        movieTitle = "Avengers: Endgame",
                        rating = 5,
                        comment = "Una película épica que cierra perfectamente la saga."
                    ),
                    ReviewItem(
                        movieTitle = "The Dark Knight",
                        rating = 5,
                        comment = "Obra maestra del cine de superhéroes."
                    ),
                    ReviewItem(
                        movieTitle = "Inception",
                        rating = 4,
                        comment = "Compleja pero brillante. Nolan en su mejor forma."
                    )
                )
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reviews = reviews
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun editReview(review: ReviewItem) {
        // Implementar edición de reseña
    }
    
    fun deleteReview(review: ReviewItem) {
        viewModelScope.launch {
            try {
                val updatedReviews = _uiState.value.reviews.filter { it != review }
                _uiState.value = _uiState.value.copy(reviews = updatedReviews)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}