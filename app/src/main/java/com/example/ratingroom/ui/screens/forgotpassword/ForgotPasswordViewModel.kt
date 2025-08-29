package com.example.ratingroom.ui.screens.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ForgotPasswordViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(ForgotPasswordUIState())
    val uiState: StateFlow<ForgotPasswordUIState> = _uiState.asStateFlow()
    
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }
    
    fun sendRecoveryEmail(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                delay(1000) // Simular envío
                
                if (_uiState.value.email.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Por favor ingresa tu email"
                    )
                } else if (!_uiState.value.email.contains("@")) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Por favor ingresa un email válido"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Se ha enviado un enlace de recuperación a tu email"
                    )
                    onSuccess()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
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