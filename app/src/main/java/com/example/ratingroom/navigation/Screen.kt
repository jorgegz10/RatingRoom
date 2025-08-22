package com.example.ratingroom.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    // Pantallas de autenticación
    object Login : Screen("login", "Iniciar Sesión")
    object Register : Screen("register", "Registrarse")
    object ForgotPassword : Screen("forgot_password", "Recuperar Contraseña")

    // Pantallas principales
    object MainMenu : Screen("main_menu", "Inicio", Icons.Default.Home)
    object EditProfile : Screen("edit_profile", "Mi Perfil", Icons.Default.Person)
    // Pantalla de lista personalizada: título "Mi Lista" y icono Flag
    object ListScreen : Screen("list", "Mi Lista", Icons.Default.Flag)
    object Friends : Screen("friends", "Amigos", Icons.Default.People)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
    object Settings : Screen("settings", "Configuración", Icons.Default.Settings)

    // Pantallas de detalle
    data class MovieDetail(val movieId: String) : Screen("movie_detail/$movieId", "Detalle de Película")

    companion object {
        // Lista de pantallas principales para el drawer
        // Orden: MainMenu, EditProfile, ListScreen ("Mi Lista"), Friends, Favorites, Settings
        val mainScreens = listOf(
            MainMenu,
            EditProfile,
            ListScreen,
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

        // Función para obtener pantalla por ruta
        fun fromRoute(route: String): Screen? {
            return when {
                route == Login.route -> Login
                route == Register.route -> Register
                route == ForgotPassword.route -> ForgotPassword
                route == MainMenu.route -> MainMenu
                route == EditProfile.route -> EditProfile
                route == Friends.route -> Friends
                route == Favorites.route -> Favorites
                route == Settings.route -> Settings
                route == ListScreen.route -> ListScreen
                route.startsWith("movie_detail/") -> {
                    val movieId = route.substringAfter("movie_detail/")
                    MovieDetail(movieId)
                }
                else -> null
            }
        }
    }
}
