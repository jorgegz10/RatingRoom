package com.example.ratingroom.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratingroom.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EditProfileUIState())
    val uiState: StateFlow<EditProfileUIState> = _uiState.asStateFlow()
    
    init {
        loadCurrentProfile()
    }
    
    private fun loadCurrentProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val userProfile = authRepository.getUserProfile()
                
                if (userProfile != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        displayName = userProfile.fullName ?: "",
                        email = userProfile.email,
                        biography = userProfile.biography ?: "",
                        location = userProfile.location ?: "",
                        favoriteGenre = userProfile.favoriteGenre ?: "",
                        birthdate = userProfile.birthdate ?: "",
                        website = userProfile.website ?: ""
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar el perfil"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error al cargar perfil"
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
                
                // Guardar perfil usando AuthRepository
                val result = authRepository.updateUserProfile(
                    displayName = currentState.displayName,
                    email = currentState.email,
                    biography = currentState.biography,
                    location = currentState.location,
                    favoriteGenre = currentState.favoriteGenre,
                    birthdate = currentState.birthdate,
                    website = currentState.website
                )
                
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        successMessage = "Perfil actualizado exitosamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        errorMessage = result.errorMessage ?: "Error al actualizar perfil"
                    )
                }
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