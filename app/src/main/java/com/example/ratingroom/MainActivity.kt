package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.repository.FriendsRepository
import com.example.ratingroom.ui.screens.*
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.GradientBackground
import com.example.ratingroom.ui.utils.ProfileMenu

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
    // NAV
    var currentScreen by remember { mutableStateOf("login") }
    var profileMenuExpanded by remember { mutableStateOf(false) }

    // MainMenu
    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Todos") }
    var filterExpanded by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    // Perfil / EditProfile
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var favoriteGenre by remember { mutableStateOf("Género Favorito") }
    var birthdate by remember { mutableStateOf("mm / dd / yyyy") }
    var website by remember { mutableStateOf("") }

    // ForgotPassword
    var forgotPasswordEmail by remember { mutableStateOf("") }

    // Friends
    var friendsSearchQuery by remember { mutableStateOf("") }
    var selectedFriendsTab by remember { mutableStateOf(0) }

    // TOP BAR
    val showTopBar = currentScreen in listOf("mainMenu", "editProfile", "movieDetail")
    val topBarTitle = when (currentScreen) {
        "mainMenu"   -> stringResource(id = R.string.main_menu_title)
        "editProfile"-> stringResource(id = R.string.edit_profile_title)
        "movieDetail"-> selectedMovie?.title ?: ""
        else         -> ""
    }

    // UI
    GradientBackground {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    TopAppBar(
                        title = { Text(topBarTitle, color = Color.White) },
                        navigationIcon = {
                            when (currentScreen) {
                                "mainMenu" -> {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logoratingroom),
                                            contentDescription = stringResource(id = R.string.content_desc_logo),
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                                "editProfile" -> {
                                    IconButton(onClick = { currentScreen = "profile" }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = stringResource(id = R.string.back_button),
                                            tint = Color.White
                                        )
                                    }
                                }
                                "movieDetail" -> {
                                    IconButton(onClick = { currentScreen = "mainMenu" }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = stringResource(id = R.string.back_button),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        },
                        actions = {
                            when (currentScreen) {
                                "mainMenu" -> {
                                    ProfileMenu(
                                        expanded = profileMenuExpanded,
                                        onExpandedChange = { profileMenuExpanded = it },
                                        onProfileClick = {
                                            profileMenuExpanded = false
                                            currentScreen = "profile"
                                        },
                                        onFriendsClick = {
                                            profileMenuExpanded = false
                                            currentScreen = "friends"
                                        },
                                        onLogoutClick = {
                                            profileMenuExpanded = false
                                            currentScreen = "login"
                                        }
                                    )
                                }
                                "editProfile" -> {
                                    IconButton(onClick = {
                                        currentScreen = "profile"
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Save,
                                            contentDescription = stringResource(id = R.string.save_button),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
                    )
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            // RUTAS
            when (currentScreen) {
                "login" -> LoginScreen(
                    onLoginClick = { _, _ -> currentScreen = "mainMenu" },
                    onForgotPasswordClick = { currentScreen = "forgotPassword" },
                    onRegisterClick = { currentScreen = "register" }
                )

                "register" -> RegisterScreen(
                    onRegisterClick = { _, _, _, _, _, _ -> currentScreen = "login" },
                    onLoginClick = { currentScreen = "login" }
                )

                "forgotPassword" -> ForgotPasswordScreen(
                    email = forgotPasswordEmail,
                    onEmailChange = { forgotPasswordEmail = it },
                    onSendRecoveryClick = { currentScreen = "login" },
                    onBackToLoginClick = { currentScreen = "login" }
                )

                "mainMenu" -> MainMenuScreen(
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

                "profile" -> ProfileScreen(
                    name = if (displayName.isBlank()) "Usuario" else displayName,
                    email = if (email.isBlank()) "correo@ejemplo.com" else email,
                    memberSince = "Enero 2024",
                    favoriteGenre = favoriteGenre,
                    reviewsCount = 3,
                    averageRating = 4.7,
                    onBackClick = { currentScreen = "mainMenu" },
                    onEditClick = { currentScreen = "editProfile" }
                )

                "editProfile" -> EditProfileScreen(
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

                "movieDetail" -> selectedMovie?.let { movie ->
                    MovieDetailScreen(
                        movie = movie,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                "friends" -> FriendsScreen(
                    searchQuery = friendsSearchQuery,
                    onSearchQueryChange = { friendsSearchQuery = it },
                    selectedTab = selectedFriendsTab,
                    onTabSelected = { selectedFriendsTab = it },
                    onFriendAction = { friend, action ->
                        when (action) {
                            "message"        -> println("Mensaje a ${friend.name}")
                            "add_friend"     -> FriendsRepository.addFriend(friend.id)
                            "remove_friend"  -> FriendsRepository.removeFriend(friend.id)
                            "follow"         -> FriendsRepository.followUser(friend.id)
                            "unfollow"       -> FriendsRepository.unfollowUser(friend.id)
                        }
                    },
                    onBackClick = { currentScreen = "mainMenu" }
                )
            }
        }
    }
}
