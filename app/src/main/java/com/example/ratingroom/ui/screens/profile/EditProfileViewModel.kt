package com.example.ratingroom.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class EditProfileViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(EditProfileUIState())
    val uiState: StateFlow<EditProfileUIState> = _uiState.asStateFlow()
    
    init {
        loadCurrentProfile()
    }
    
    private fun loadCurrentProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                delay(1000) // Simular carga
                
                // Cargar datos actuales del perfil
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    displayName = "Usuario Demo",
                    email = "usuario@demo.com",
                    biography = "Amante del cine y las buenas historias.",
                    location = "Ciudad de México",
                    favoriteGenre = "Acción",
                    birthdate = "01/01/1990",
                    website = "https://miwebsite.com"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun onDisplayNameChange(displayName: String) {
        _uiState.value = _uiState.value.copy(
            displayName = displayName,
            errorMessage = null
        )
    }
    
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }
    
    fun onBiographyChange(biography: String) {
        _uiState.value = _uiState.value.copy(
            biography = biography,
            errorMessage = null
        )
    }
    
    fun onLocationChange(location: String) {
        _uiState.value = _uiState.value.copy(
            location = location,
            errorMessage = null
        )
    }
    
    fun onFavoriteGenreChange(favoriteGenre: String) {
        _uiState.value = _uiState.value.copy(
            favoriteGenre = favoriteGenre,
            errorMessage = null
        )
    }
    
    fun onBirthdateChange(birthdate: String) {
        _uiState.value = _uiState.value.copy(
            birthdate = birthdate,
            errorMessage = null
        )
    }
    
    fun onWebsiteChange(website: String) {
        _uiState.value = _uiState.value.copy(
            website = website,
            errorMessage = null
        )
    }
    
    fun saveProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
            
            try {
                // Validaciones
                val currentState = _uiState.value
                
                when {
                    currentState.displayName.isBlank() -> {
                        _uiState.value = _uiState.value.copy(
                            isSaving = false,
                            errorMessage = "El nombre para mostrar es requerido"
                        )
                        return@launch
                    }
                    currentState.email.isBlank() || !currentState.email.contains("@") -> {
                        _uiState.value = _uiState.value.copy(
                            isSaving = false,
                            errorMessage = "Email inválido"
                        )
                        return@launch
                    }
                }
                
                // Simular guardado
                delay(2000)
                
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    successMessage = "Perfil actualizado exitosamente"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = e.message ?: "Error al guardar"
                )
            }
        }
    }
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}