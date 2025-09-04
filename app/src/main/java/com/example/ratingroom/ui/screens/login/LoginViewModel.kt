package com.example.ratingroom.ui.screens.login

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()
    
    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            errorMessage = null
        )
    }
    
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }
    
    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                val currentState = _uiState.value
                
                // Validaciones básicas
                when {
                    currentState.username.isBlank() -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "El email es requerido"
                        )
                        return@launch
                    }
                    !currentState.username.contains("@") -> {
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
                }
                
                // Realizar login usando AuthRepository
                println("LoginViewModel: Iniciando login")
                val result = authRepository.signIn(
                    email = currentState.username,
                    password = currentState.password
                )
                
                println("LoginViewModel: Resultado recibido - isSuccess: ${result.isSuccess}")
                if (result.isSuccess) {
                    println("LoginViewModel: Login exitoso, llamando onSuccess")
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    println("LoginViewModel: Antes de llamar onSuccess")
                    onSuccess()
                    println("LoginViewModel: onSuccess() ejecutado")
                } else {
                    println("LoginViewModel: Login falló - ${result.errorMessage}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage ?: "Error de autenticación"
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
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}