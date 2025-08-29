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
    object Profile : Screen("profile", "Mi Perfil", Icons.Default.Person)
    object EditProfile : Screen("edit_profile", "Editar Perfil")
    object Friends : Screen("friends", "Amigos", Icons.Default.People)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
    object Settings : Screen("settings", "Configuración", Icons.Default.Settings)
    object List : Screen("list", "Lista de Películas", Icons.Default.List)
    object Reviews : Screen("reviews", "Mis Reseñas", Icons.Default.RateReview)
    
    // Pantallas de detalle con argumentos
    object MovieDetail : Screen("movie_detail/{movieId}", "Detalle de Película") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
    
    object Synopsis : Screen("synopsis/{movieId}", "Sinopsis") {
        fun createRoute(movieId: Int) = "synopsis/$movieId"
    }
    
    companion object {
        // Lista de pantallas principales para el drawer
        val mainScreens = listOf(
            MainMenu,
            List,
            Reviews,
            Profile,  // Cambiado de EditProfile a Profile
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
