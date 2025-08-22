package com.example.ratingroom

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Estados globales
    var isDrawerOpen by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }
    
    // Estados para MainMenu
    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Todos") }
    var filterExpanded by remember { mutableStateOf(false) }
    
    // Estados para EditProfile
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var favoriteGenre by remember { mutableStateOf("Género Favorito") }
    var birthdate by remember { mutableStateOf("mm / dd / yyyy") }
    var website by remember { mutableStateOf("") }
    
    // Estados para ForgotPassword
    var forgotPasswordEmail by remember { mutableStateOf("") }
    
    // Estados para Friends
    var friendsSearchQuery by remember { mutableStateOf("") }
    var selectedFriendsTab by remember { mutableStateOf(0) }

    // Función de navegación
    val navigateToScreen: (String) -> Unit = { route ->
        navController.navigate(route) {
            launchSingleTop = true
        }
        isDrawerOpen = false
    }
    
    // Función para ir hacia atrás
    val navigateBack: () -> Unit = {
        if (!navController.popBackStack()) {
            navController.navigate(Screen.MainMenu.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
        isDrawerOpen = false
    }
    
    // Determinar pantalla actual para configuraciones
    val currentScreen = when (currentRoute) {
        Screen.Login.route -> Screen.Login
        Screen.Register.route -> Screen.Register
        Screen.ForgotPassword.route -> Screen.ForgotPassword
        Screen.MainMenu.route -> Screen.MainMenu
        Screen.EditProfile.route -> Screen.EditProfile
        Screen.Friends.route -> Screen.Friends
        Screen.Favorites.route -> Screen.Favorites
        Screen.Settings.route -> Screen.Settings
        else -> if (currentRoute?.startsWith("movie_detail/") == true) Screen.MovieDetail else Screen.MainMenu
    }
    
    // Configuración de TopBar
    val topBarConfig = when (currentScreen) {
        Screen.EditProfile -> TopBarConfig(
            title = stringResource(id = R.string.edit_profile_title),
            showBackButton = true,
            showSaveButton = true,
            onBackClick = navigateBack,
            onSaveClick = navigateBack
        )
        Screen.MovieDetail -> TopBarConfig(
            title = selectedMovie?.title ?: "",
            showBackButton = true,
            onBackClick = navigateBack
        )
        else -> null
    }

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
        ) {
            // Contenido principal con NavHost
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    if (currentScreen in listOf(Screen.MainMenu, Screen.Friends) && currentScreen !in Screen.authScreens) {
                        ModernTopBar(
                            title = currentScreen.title,
                            onMenuClick = { isDrawerOpen = true }
                        )
                    } else {
                        topBarConfig?.let { config ->
                            AppTopBar(config = config)
                        }
                    }
                },
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0)
            ) { innerPadding ->
                
                // NavHost - El corazón de la navegación
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // Pantallas de autenticación
                    composable(Screen.Login.route) {
                        LoginScreen(
                            onLoginClick = { _, _ -> 
                                navController.navigate(Screen.MainMenu.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            },
                            onForgotPasswordClick = { navigateToScreen(Screen.ForgotPassword.route) },
                            onRegisterClick = { navigateToScreen(Screen.Register.route) }
                        )
                    }
                    
                    composable(Screen.Register.route) {
                        RegisterScreen(
                            onRegisterClick = { _, _, _, _, _, _ -> navigateBack() },
                            onLoginClick = { navigateBack() }
                        )
                    }
                    
                    composable(Screen.ForgotPassword.route) {
                        ForgotPasswordScreen(
                            email = forgotPasswordEmail,
                            onEmailChange = { forgotPasswordEmail = it },
                            onSendRecoveryClick = { navigateBack() },
                            onBackToLoginClick = { navigateBack() }
                        )
                    }
                    
                    // Pantallas principales
                    composable(Screen.MainMenu.route) {
                        MainMenuScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            selectedGenre = selectedGenre,
                            onGenreSelected = { selectedGenre = it },
                            filterExpanded = filterExpanded,
                            onFilterExpandedChange = { filterExpanded = it },
                            onMovieClick = { id ->
                                Log.d("Ayuda", "$id")
                                navController.navigate(Screen.MovieDetail.createRoute(id))
                            }
                        )
                    }
                    
                    composable(Screen.EditProfile.route) {
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
                            onWebsiteChange = { website = it }
                        )
                    }
                    
                    composable(Screen.Friends.route) {
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
                            }
                        )
                    }
                    
                    composable(Screen.Favorites.route) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text("Pantalla de Favoritos - En desarrollo")
                        }
                    }
                    
                    composable(Screen.Settings.route) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text("Pantalla de Configuración - En desarrollo")
                        }
                    }
                    
                    // Pantalla de detalle con parámetros
                    // En la sección del composable de MovieDetail, cambiar:
                    composable(
                        route = "movie_detail/{movieId}",
                        arguments = listOf(
                            navArgument("movieId") { 
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId")

                        val movie = MovieRepository.getMovieById(movieId!!)


                            MovieDetailScreen(
                                movie = movie!!,
                                onShowMore = { /* Acción para mostrar más detalles */ },
                                modifier = Modifier.fillMaxSize()
                            )

                    }
                }
            }

            // Navigation Drawer
            if (currentScreen !in Screen.authScreens) {
                ModernNavigationDrawer(
                    isOpen = isDrawerOpen,
                    onToggle = { isDrawerOpen = !isDrawerOpen },
                    currentScreen = currentScreen,
                    onNavigate = { screen -> navigateToScreen(screen.route) },
                    onLogout = { 
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                        isDrawerOpen = false
                    }
                )
            }
        }
    }
}
