package com.example.ratingroom.ui.screens.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratingroom.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    
    // Estado UI para la MainActivity
    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()
    
    // Funciones para actualizar el estado
    fun updateDrawerState(isOpen: Boolean) {
        _uiState.update { it.copy(isDrawerOpen = isOpen) }
    }
    
    fun toggleDrawer() {
        _uiState.update { it.copy(isDrawerOpen = !it.isDrawerOpen) }
    }
    
    // Funciones de navegación
    fun navigateToScreen(route: String) {
        // Solo actualiza el estado, la navegación real se maneja en la UI
        _uiState.update { it.copy(currentRoute = route) }
    }
    
    fun navigateBack() {
        // La lógica de navegación hacia atrás se maneja en la UI
        // Aquí solo podemos registrar el evento
        println("MainViewModel: navigateBack() iniciado")
    }
    
    // Determinar si mostrar drawer y topbar basado en la ruta actual
    fun isAuthScreen(route: String): Boolean {
        return route in listOf(
            Screen.Login.route,
            Screen.Register.route,
            Screen.ForgotPassword.route
        )
    }
    
    // Obtener el título de la pantalla actual
    fun getCurrentTitle(route: String): String {
        return when {
            route == Screen.MainMenu.route -> Screen.MainMenu.title
            route == Screen.Profile.route -> Screen.Profile.title
            route == Screen.Friends.route -> Screen.Friends.title
            route == Screen.List.route -> Screen.List.title
            route == Screen.Reviews.route -> Screen.Reviews.title
            route == Screen.Favorites.route -> Screen.Favorites.title
            route == Screen.Settings.route -> Screen.Settings.title
            route == Screen.EditProfile.route -> Screen.EditProfile.title
            route.startsWith("movie_detail/") -> Screen.MovieDetail.title
            route.startsWith("synopsis/") -> Screen.Synopsis.title
            else -> "RatingRoom"
        }
    }
}