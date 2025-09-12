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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()
    
    init {
        loadProfile()
    }
    
    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            println("ProfileViewModel.loadProfile: Iniciando carga de perfil")
            
            try {
                val userProfile = authRepository.getUserProfile()
                println("ProfileViewModel.loadProfile: Perfil recibido de AuthRepository: ${userProfile != null}")
                
                if (userProfile != null) {
                    println("ProfileViewModel.loadProfile: Perfil cargado con éxito")
                    println("ProfileViewModel.loadProfile: profileImageUrl: ${userProfile.profileImageUrl}")
                    
                    // Asegurarse de que la URL de la imagen no sea nula o vacía
                    val imageUrl = if (!userProfile.profileImageUrl.isNullOrEmpty()) {
                        println("ProfileViewModel.loadProfile: Usando URL de imagen de Firebase: ${userProfile.profileImageUrl}")
                        userProfile.profileImageUrl
                    } else {
                        println("ProfileViewModel.loadProfile: No hay URL de imagen disponible")
                        null
                    }
                    
                    val profileData = ProfileData(
                        name = userProfile.fullName ?: "Usuario",
                        email = userProfile.email,
                        memberSince = "Enero 2024", // Podrías calcular esto desde createdAt
                        favoriteGenre = userProfile.favoriteGenre ?: "No especificado",
                        reviewsCount = 0, // Esto vendría de otra fuente
                        averageRating = 0.0, // Esto vendría de otra fuente
                        profileImageUrl = imageUrl
                    )
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        profileData = profileData
                    )
                    println("ProfileViewModel.loadProfile: Estado actualizado con profileImageUrl: ${_uiState.value.profileData?.profileImageUrl}")
                } else {
                    println("ProfileViewModel.loadProfile: No se pudo cargar el perfil")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar el perfil"
                    )
                }
            } catch (e: Exception) {
                println("ProfileViewModel.loadProfile: ERROR al cargar perfil: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error al cargar perfil"
                )
            }
        }
    }
    
    fun onDarkModeChange(isDarkMode: Boolean) {
        _uiState.value = _uiState.value.copy(isDarkMode = isDarkMode)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    fun refreshProfile() {
        println("ProfileViewModel.refreshProfile: Refrescando perfil")
        loadProfile()
    }
}