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
import com.example.ratingroom.navigation.Screen

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
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }
    var isDrawerOpen by remember { mutableStateOf(false) }

    // Estados para MainMenu (hoisted)
    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Todos") }
    var filterExpanded by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    // Estado para ListScreen (hoisted)
    var selectedListTab by remember { mutableStateOf(0) }

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
    val navigateToScreen: (Screen) -> Unit = { screen ->
        currentScreen = screen
        isDrawerOpen = false
    }

    // Configuración de TopBar simplificada (solo para pantallas que necesitan botón atrás)
    val topBarConfig = when (currentScreen) {
        is Screen.EditProfile -> TopBarConfig(
            title = stringResource(id = R.string.edit_profile_title),
            showBackButton = true,
            showSaveButton = true,
            onBackClick = { currentScreen = Screen.MainMenu },
            onSaveClick = { currentScreen = Screen.MainMenu }
        )
        is Screen.MovieDetail -> TopBarConfig(
            title = selectedMovie?.title ?: "",
            showBackButton = true,
            onBackClick = { currentScreen = Screen.MainMenu }
        )
        else -> null
    }

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
        ) {
            // Contenido principal
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    // TopBar solo para pantallas principales con drawer
                    // Agregamos Screen.ListScreen para que muestre el ModernTopBar con menú
                    if (currentScreen in listOf(Screen.MainMenu, Screen.Friends, Screen.ListScreen) && currentScreen !in Screen.authScreens) {
                        ModernTopBar(
                            title = currentScreen.title,
                            onMenuClick = { isDrawerOpen = true }
                        )
                    } else {
                        // TopBar tradicional para otras pantallas
                        topBarConfig?.let { config ->
                            AppTopBar(config = config)
                        }
                    }
                },
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0)
            ) { innerPadding ->
                // Contenido de las pantallas
                when (currentScreen) {
                    is Screen.Login -> {
                        LoginScreen(
                            onLoginClick = { _, _ -> currentScreen = Screen.MainMenu },
                            onForgotPasswordClick = { currentScreen = Screen.ForgotPassword },
                            onRegisterClick = { currentScreen = Screen.Register }
                        )
                    }

                    is Screen.Register -> {
                        RegisterScreen(
                            onRegisterClick = { _, _, _, _, _, _ -> currentScreen = Screen.Login },
                            onLoginClick = { currentScreen = Screen.Login }
                        )
                    }

                    is Screen.ForgotPassword -> {
                        ForgotPasswordScreen(
                            email = forgotPasswordEmail,
                            onEmailChange = { forgotPasswordEmail = it },
                            onSendRecoveryClick = { currentScreen = Screen.Login },
                            onBackToLoginClick = { currentScreen = Screen.Login }
                        )
                    }

                    is Screen.MainMenu -> {
                        MainMenuScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            selectedGenre = selectedGenre,
                            onGenreSelected = { selectedGenre = it },
                            filterExpanded = filterExpanded,
                            onFilterExpandedChange = { filterExpanded = it },
                            onMovieClick = { movie ->
                                selectedMovie = movie
                                currentScreen = Screen.MovieDetail(movie.id.toString())
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    is Screen.ListScreen -> {
                        ListScreen(
                            selectedTab = selectedListTab,
                            onTabSelected = { selectedListTab = it },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    is Screen.EditProfile -> {
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

                    is Screen.MovieDetail -> {
                        selectedMovie?.let { movie ->
                            MovieDetailScreen(
                                movie = movie,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    is Screen.Friends -> {
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

                    is Screen.Favorites -> {
                        // Implementar pantalla de favoritos
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text("Pantalla de Favoritos - En desarrollo")
                        }
                    }

                    is Screen.Settings -> {
                        // Implementar pantalla de configuración
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text("Pantalla de Configuración - En desarrollo")
                        }
                    }
                }
            }

            // Navigation Drawer
            if (currentScreen !in Screen.authScreens) {
                ModernNavigationDrawer(
                    isOpen = isDrawerOpen,
                    onToggle = { isDrawerOpen = !isDrawerOpen },
                    currentScreen = currentScreen,
                    onNavigate = navigateToScreen,
                    onLogout = {
                        currentScreen = Screen.Login
                        isDrawerOpen = false
                    }
                )
            }
        }
    }
}
