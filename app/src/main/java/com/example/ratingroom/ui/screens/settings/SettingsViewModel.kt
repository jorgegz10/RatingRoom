package com.example.ratingroom.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUIState())
    val uiState: StateFlow<SettingsUIState> = _uiState.asStateFlow()
    
    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // TODO: Cargar configuraciones desde SharedPreferences o base de datos
                // Por ahora usamos valores por defecto
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    notificationsEnabled = true,
                    darkModeEnabled = false,
                    selectedLanguage = "es"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun onNotificationsChange(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(notificationsEnabled = enabled)
        // TODO: Guardar en SharedPreferences
    }
    
    fun onDarkModeChange(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(darkModeEnabled = enabled)
        // TODO: Guardar en SharedPreferences y aplicar tema
    }
    
    fun onLanguageChange(language: String) {
        _uiState.value = _uiState.value.copy(selectedLanguage = language)
        // TODO: Guardar en SharedPreferences y cambiar idioma
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}