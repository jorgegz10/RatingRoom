package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.ratingroom.ui.screens.*
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.repository.FriendsRepository
import com.example.ratingroom.navigation.Screen
import com.example.ratingroom.navigation.Screen.*

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
    var currentScreen by remember { mutableStateOf<Screen>(Login) }
    var isDrawerOpen by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Todos") }
    var filterExpanded by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    var selectedListTab by remember { mutableStateOf(0) }

    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var favoriteGenre by remember { mutableStateOf("Género Favorito") }
    var birthdate by remember { mutableStateOf("mm / dd / yyyy") }
    var website by remember { mutableStateOf("") }

    var forgotPasswordEmail by remember { mutableStateOf("") }

    var friendsSearchQuery by remember { mutableStateOf("") }
    var selectedFriendsTab by remember { mutableStateOf(0) }

    val navigateToScreen: (Screen) -> Unit = { screen ->
        currentScreen = screen
        isDrawerOpen = false
    }

    val topBarConfig = when (currentScreen) {
        is EditProfile -> TopBarConfig(
            title = stringResource(id = R.string.edit_profile_title),
            showBackButton = true,
            showSaveButton = true,
            onBackClick = { currentScreen = MainMenu },
            onSaveClick = { currentScreen = MainMenu }
        )
        is MovieDetail -> TopBarConfig(
            title = selectedMovie?.title ?: "",
            showBackButton = true,
            onBackClick = { currentScreen = MainMenu }
        )
        else -> null
    }

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    if (currentScreen in listOf(MainMenu, Friends, ListScreen) && currentScreen !in Screen.authScreens) {
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
                when (currentScreen) {
                    is Login -> {
                        LoginScreen(
                            onLoginClick = { _, _ -> currentScreen = MainMenu },
                            onForgotPasswordClick = { currentScreen = ForgotPassword },
                            onRegisterClick = { currentScreen = Register }
                        )
                    }

                    is Register -> {
                        RegisterScreen(
                            onRegisterClick = { _, _, _, _, _, _ -> currentScreen = Login },
                            onLoginClick = { currentScreen = Login }
                        )
                    }

                    is ForgotPassword -> {
                        ForgotPasswordScreen(
                            email = forgotPasswordEmail,
                            onEmailChange = { forgotPasswordEmail = it },
                            onSendRecoveryClick = { currentScreen = Login },
                            onBackToLoginClick = { currentScreen = Login }
                        )
                    }

                    is MainMenu -> {
                        MainMenuScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            selectedGenre = selectedGenre,
                            onGenreSelected = { selectedGenre = it },
                            filterExpanded = filterExpanded,
                            onFilterExpandedChange = { filterExpanded = it },
                            onMovieClick = { movie ->
                                selectedMovie = movie
                                currentScreen = MovieDetail(movie.id.toString())
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    is ListScreen -> {
                        ListScreen(
                            selectedTab = selectedListTab,
                            onTabSelected = { selectedListTab = it },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    is EditProfile -> {
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

                    is MovieDetail -> {
                        selectedMovie?.let { movie ->
                            MovieDetailScreen(
                                movie = movie,
                                onShowMore = {
                                    currentScreen = Synopsis(movie.id.toString())
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    is Friends -> {
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

                    is Favorites -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Pantalla de Favoritos - En desarrollo")
                        }
                    }

                    is Settings -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Pantalla de Configuración - En desarrollo")
                        }
                    }

                    MyReviews -> {
                        ReviewsScreen(
                            onBack = { currentScreen = MainMenu }
                        )
                    }

                    is Synopsis -> {
                        val movie = selectedMovie
                        if (movie != null) {
                            SynopsisScreen(
                                movie = movie,
                                onBackClick = { currentScreen = MovieDetail(movie.id.toString()) },
                                modifier = Modifier.padding(innerPadding)
                            )
                        } else {
                            currentScreen = MainMenu
                        }
                    }
                }
            }

            if (currentScreen !in Screen.authScreens) {
                ModernNavigationDrawer(
                    isOpen = isDrawerOpen,
                    onToggle = { isDrawerOpen = !isDrawerOpen },
                    currentScreen = currentScreen,
                    onNavigate = navigateToScreen,
                    onLogout = {
                        currentScreen = Login
                        isDrawerOpen = false
                    }
                )
            }
        }
    }
}
