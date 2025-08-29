package com.example.ratingroom.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class RegisterViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState> = _uiState.asStateFlow()
    
    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            errorMessage = null
        )
    }
    
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }
    
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }
    
    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            errorMessage = null
        )
    }
    
    fun onBirthdateChange(birthdate: String) {
        _uiState.value = _uiState.value.copy(
            birthdate = birthdate,
            errorMessage = null
        )
    }
    
    fun onGenreChange(genre: String) {
        _uiState.value = _uiState.value.copy(
            genre = genre,
            errorMessage = null
        )
    }
    
    fun onFullNameChange(fullName: String) {
        _uiState.value = _uiState.value.copy(
            fullName = fullName,
            errorMessage = null
        )
    }
    
    fun onFavoriteGenreChange(favoriteGenre: String) {
        _uiState.value = _uiState.value.copy(
            favoriteGenre = favoriteGenre,
            errorMessage = null
        )
    }
    
    fun onBirthYearChange(birthYear: String) {
        _uiState.value = _uiState.value.copy(
            birthYear = birthYear,
            errorMessage = null
        )
    }
    
    fun onAcceptTermsChange(acceptTerms: Boolean) {
        _uiState.value = _uiState.value.copy(
            acceptTerms = acceptTerms,
            errorMessage = null
        )
    }
    
    fun register() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                // Validaciones
                val currentState = _uiState.value
                
                when {
                    currentState.username.isBlank() -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "El nombre de usuario es requerido"
                        )
                        return@launch
                    }
                    currentState.email.isBlank() || !currentState.email.contains("@") -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Email inválido"
                        )
                        return@launch
                    }
                    currentState.password.length < 6 -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "La contraseña debe tener al menos 6 caracteres"
                        )
                        return@launch
                    }
                    currentState.password != currentState.confirmPassword -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Las contraseñas no coinciden"
                        )
                        return@launch
                    }
                }
                
                // Simular registro
                delay(2000)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Registro exitoso"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error desconocido"
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