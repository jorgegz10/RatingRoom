package com.example.ratingroom.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratingroom.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainMenuViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainMenuUIState())
    val uiState: StateFlow<MainMenuUIState> = _uiState.asStateFlow()
    
    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    
    fun onGenreSelected(genre: String) {
        _uiState.value = _uiState.value.copy(
            selectedGenre = genre,
            filterExpanded = false
        )
    }
    
    fun onFilterExpandedChange(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(filterExpanded = expanded)
    }
}