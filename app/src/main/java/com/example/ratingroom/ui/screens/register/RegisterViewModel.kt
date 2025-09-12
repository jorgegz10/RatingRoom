package com.example.ratingroom.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratingroom.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
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
    
    fun register(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                // Validaciones
                val currentState = _uiState.value
                
                when {
                    currentState.fullName.isBlank() -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "El nombre completo es requerido"
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
                
                // Preparar campos opcionales (solo si no están vacíos)
                val favoriteGenre = if (currentState.favoriteGenre.isNotBlank()) {
                    currentState.favoriteGenre
                } else null
                
                val birthYear = if (currentState.birthYear.isNotBlank()) {
                    currentState.birthYear
                } else null
                
                // Realizar registro usando AuthRepository
                println("RegisterViewModel: Iniciando registro")
                val result = authRepository.signUp(
                    email = currentState.email,
                    password = currentState.password,
                    displayName = currentState.fullName
                )
                
                println("RegisterViewModel: Resultado recibido - isSuccess: ${result.isSuccess}")
                if (result.isSuccess) {
                    println("RegisterViewModel: Registro exitoso, actualizando UI y llamando onSuccess")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Registro exitoso"
                    )
                    println("RegisterViewModel: Antes de llamar onSuccess")
                    onSuccess()
                    println("RegisterViewModel: onSuccess() ejecutado")
                } else {
                    println("RegisterViewModel: Registro falló - ${result.errorMessage}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage ?: "Error al crear cuenta"
                    )
                }
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