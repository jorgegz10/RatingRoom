package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.ratingroom.ui.screens.*
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*
import com.example.ratingroom.data.repository.MovieRepository
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.repository.FriendsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RatingRoomTheme {
                RatingRoomApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingRoomApp() {
    // STATE HOISTING - Todos los estados principales aquí
    var currentScreen by remember { mutableStateOf("login") }
    var isDrawerOpen by remember { mutableStateOf(false) }
    
    // Estados para MainMenu (hoisted)
    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Todos") }
    var filterExpanded by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }
    
    // Estados para EditProfile (hoisted)
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var favoriteGenre by remember { mutableStateOf("Género Favorito") }
    var birthdate by remember { mutableStateOf("mm / dd / yyyy") }
    var website by remember { mutableStateOf("") }
    
    // Estados para ForgotPassword (hoisted)
    var forgotPasswordEmail by remember { mutableStateOf("") }
    
    // Estados para Friends (hoisted)
    var friendsSearchQuery by remember { mutableStateOf("") }
    var selectedFriendsTab by remember { mutableStateOf(0) }

    // Función de navegación
    val navigateToScreen: (String) -> Unit = { screen ->
        currentScreen = screen
        isDrawerOpen = false
    }

    // Configuración de TopBar simplificada (solo para pantallas que necesitan botón atrás)
    val topBarConfig = when (currentScreen) {
        "editProfile" -> TopBarConfig(
            title = stringResource(id = R.string.edit_profile_title),
            showBackButton = true,
            showSaveButton = true,
            onBackClick = { currentScreen = "mainMenu" },
            onSaveClick = { currentScreen = "mainMenu" }
        )
        "movieDetail" -> TopBarConfig(
            title = selectedMovie?.title ?: "",
            showBackButton = true,
            onBackClick = { currentScreen = "mainMenu" }
        )
        else -> null
    }

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            // Contenido principal
            Scaffold(
                topBar = {
                    // TopBar solo para pantallas principales con drawer
                    if (currentScreen in listOf("mainMenu", "friends") && currentScreen != "login" && currentScreen != "register" && currentScreen != "forgotPassword") {
                        ModernTopBar(
                            title = when (currentScreen) {
                                "mainMenu" -> stringResource(id = R.string.main_menu_title)
                                "friends" -> "Amigos"
                                else -> ""
                            },
                            onMenuClick = { isDrawerOpen = true }
                        )
                    } else {
                        // TopBar tradicional para otras pantallas
                        topBarConfig?.let { config ->
                            AppTopBar(config = config)
                        }
                    }
                },
                containerColor = Color.Transparent
            ) { innerPadding ->
                // Contenido de las pantallas
                when (currentScreen) {
                    "login" -> {
                        LoginScreen(
                            onLoginClick = { _, _ -> currentScreen = "mainMenu" },
                            onForgotPasswordClick = { currentScreen = "forgotPassword" },
                            onRegisterClick = { currentScreen = "register" }
                        )
                    }
                    
                    "register" -> {
                        RegisterScreen(
                            onRegisterClick = { _, _, _, _, _, _ -> currentScreen = "login" },
                            onLoginClick = { currentScreen = "login" }
                        )
                    }
                    
                    "forgotPassword" -> {
                        ForgotPasswordScreen(
                            email = forgotPasswordEmail,
                            onEmailChange = { forgotPasswordEmail = it },
                            onSendRecoveryClick = { currentScreen = "login" },
                            onBackToLoginClick = { currentScreen = "login" }
                        )
                    }
                    
                    "mainMenu" -> {
                        MainMenuScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            selectedGenre = selectedGenre,
                            onGenreSelected = { selectedGenre = it },
                            filterExpanded = filterExpanded,
                            onFilterExpandedChange = { filterExpanded = it },
                            onMovieClick = { movie ->
                                selectedMovie = movie
                                currentScreen = "movieDetail"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    
                    "editProfile" -> {
                        EditProfileScreen(
                            displayName = displayName,
                            onDisplayNameChange = { displayName = it },
                            email = email,
                            onEmailChange = { email = it },
                            biography = biography,
                            onBiographyChange = { biography = it },
                            location = location,
                            onLocationChange = { location = it },
                            favoriteGenre = favoriteGenre,
                            onFavoriteGenreChange = { favoriteGenre = it },
                            birthdate = birthdate,
                            onBirthdateChange = { birthdate = it },
                            website = website,
                            onWebsiteChange = { website = it },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    
                    "movieDetail" -> {
                        selectedMovie?.let { movie ->
                            MovieDetailScreen(
                                movie = movie,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                    
                    "friends" -> {
                        FriendsScreen(
                            searchQuery = friendsSearchQuery,
                            onSearchQueryChange = { friendsSearchQuery = it },
                            selectedTab = selectedFriendsTab,
                            onTabSelected = { selectedFriendsTab = it },
                            onFriendAction = { friend, action ->
                                when (action) {
                                    "message" -> println("Enviar mensaje a ${friend.name}")
                                    "add_friend" -> {
                                        FriendsRepository.addFriend(friend.id)
                                        println("Amigo agregado: ${friend.name}")
                                    }
                                    "remove_friend" -> {
                                        FriendsRepository.removeFriend(friend.id)
                                        println("Amigo eliminado: ${friend.name}")
                                    }
                                    "follow" -> {
                                        FriendsRepository.followUser(friend.id)
                                        println("Siguiendo a ${friend.name}")
                                    }
                                    "unfollow" -> {
                                        FriendsRepository.unfollowUser(friend.id)
                                        println("Dejando de seguir a ${friend.name}")
                                    }
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }

            // Navigation Drawer
            if (currentScreen != "login" && currentScreen != "register" && currentScreen != "forgotPassword") {
                ModernNavigationDrawer(
                    isOpen = isDrawerOpen,
                    onToggle = { isDrawerOpen = !isDrawerOpen },
                    currentScreen = currentScreen,
                    onNavigate = navigateToScreen,
                    onLogout = { 
                        currentScreen = "login"
                        isDrawerOpen = false
                    }
                )
            }
        }
    }
}
