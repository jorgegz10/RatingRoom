package com.example.ratingroom.ui.screens.forgotpassword

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
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ForgotPasswordUIState())
    val uiState: StateFlow<ForgotPasswordUIState> = _uiState.asStateFlow()
    
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }
    
    fun sendRecoveryEmail(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                val currentState = _uiState.value
                
                // Validaciones
                when {
                    currentState.email.isEmpty() -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Por favor ingresa tu email"
                        )
                        return@launch
                    }
                    !currentState.email.contains("@") -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Por favor ingresa un email válido"
                        )
                        return@launch
                    }
                }
                
                // Enviar email de recuperación usando AuthRepository
                val result = authRepository.sendPasswordResetEmail(currentState.email)
                
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Se ha enviado un enlace de recuperación a tu email"
                    )
                    onSuccess()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage ?: "Error al enviar email de recuperación"
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
    
    fun onShowHowItWorksChange(show: Boolean) {
        _uiState.value = _uiState.value.copy(showHowItWorks = show)
    }
}