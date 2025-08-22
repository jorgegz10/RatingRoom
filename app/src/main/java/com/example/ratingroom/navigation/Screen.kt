package com.example.ratingroom.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

 public sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    // Pantallas de autenticación
    object Login : Screen("login", "Iniciar Sesión")
    object Register : Screen("register", "Registrarse")
    object ForgotPassword : Screen("forgot_password", "Recuperar Contraseña")
    
    // Pantallas principales
    object MainMenu : Screen("main_menu", "Inicio", Icons.Default.Home)
    object EditProfile : Screen("edit_profile", "Mi Perfil", Icons.Default.Person)
    object Friends : Screen("friends", "Amigos", Icons.Default.People)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
    object Settings : Screen("settings", "Configuración", Icons.Default.Settings)
    
    // Pantallas de detalle con argumentos
    object MovieDetail : Screen("movie_detail/{movieId}", "Detalle de Película") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
    
    companion object {
        // Lista de pantallas principales para el drawer
        val mainScreens = listOf(
            MainMenu,
            EditProfile,
            Friends,
            Favorites,
            Settings
        )
        
        // Lista de pantallas de autenticación
        val authScreens = listOf(
            Login,
            Register,
            ForgotPassword
        )
    }
}
