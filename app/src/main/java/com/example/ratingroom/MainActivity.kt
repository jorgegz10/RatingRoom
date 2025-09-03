package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ratingroom.navigation.Screen
import com.example.ratingroom.ui.screens.favorites.FavoritesScreen
import com.example.ratingroom.ui.screens.forgotpassword.ForgotPasswordScreen
import com.example.ratingroom.ui.screens.friends.FriendsScreen
import com.example.ratingroom.ui.screens.list.ListScreen
import com.example.ratingroom.ui.screens.login.LoginScreen
import com.example.ratingroom.ui.screens.main.MainMenuScreen
import com.example.ratingroom.ui.screens.moviedetail.MovieDetailRoute
import com.example.ratingroom.ui.screens.profile.EditProfileScreen
import com.example.ratingroom.ui.screens.profile.ProfileScreen
import com.example.ratingroom.ui.screens.register.RegisterScreen
import com.example.ratingroom.ui.screens.reviews.ReviewsScreen
import com.example.ratingroom.ui.screens.settings.SettingsScreen
import com.example.ratingroom.ui.screens.synopsis.SynopsisScreen
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.GradientBackground
import com.example.ratingroom.ui.utils.ModernNavigationDrawer
import com.example.ratingroom.ui.utils.ModernTopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { RatingRoomTheme { RatingRoomApp() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingRoomApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var isDrawerOpen by remember { mutableStateOf(false) }

    val navigateToScreen: (String) -> Unit = { route ->
        navController.navigate(route) { launchSingleTop = true }
        isDrawerOpen = false
    }

    val navigateBack: () -> Unit = {
        if (!navController.popBackStack()) {
            navController.navigate(Screen.MainMenu.route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
        isDrawerOpen = false
    }

    val currentScreen = when {
        currentRoute == Screen.Login.route -> Screen.Login
        currentRoute == Screen.Register.route -> Screen.Register
        currentRoute == Screen.ForgotPassword.route -> Screen.ForgotPassword
        currentRoute == Screen.MainMenu.route -> Screen.MainMenu
        currentRoute == Screen.Profile.route -> Screen.Profile
        currentRoute == Screen.EditProfile.route -> Screen.EditProfile
        currentRoute == Screen.Friends.route -> Screen.Friends
        currentRoute == Screen.Favorites.route -> Screen.Favorites
        currentRoute == Screen.Settings.route -> Screen.Settings
        currentRoute == Screen.List.route -> Screen.List
        currentRoute == Screen.Reviews.route -> Screen.Reviews
        currentRoute?.startsWith("movie_detail/") == true -> Screen.MovieDetail
        currentRoute?.startsWith("synopsis/") == true -> Screen.Synopsis
        else -> Screen.MainMenu
    }

    val screensWithDrawer = remember {
        listOf(
            Screen.MainMenu,
            Screen.Profile,   // Perfil usa ModernTopBar con logo/menú
            Screen.Friends,
            Screen.List,
            Screen.Reviews,
            Screen.Favorites,
            Screen.Settings
        )
    }

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars.only(
            WindowInsetsSides.Horizontal)) ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),

                topBar = {
                    if (currentScreen in screensWithDrawer && currentScreen !in Screen.authScreens) {
                        ModernTopBar(
                            title = currentScreen.title,
                            onMenuClick = { isDrawerOpen = true }
                        )
                    }
                },
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0,0,0,0)
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // ---------- AUTH ----------
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
                            onLoginClick = { navigateBack() },
                            onBackClick = { navigateBack() }
                        )
                    }
                    composable(Screen.ForgotPassword.route) {
                        ForgotPasswordScreen(
                            onSendRecoveryClick = { _ -> navigateBack() },
                            onBackToLoginClick = { navigateBack() }
                        )
                    }

                    // ---------- PRINCIPALES ----------
                    composable(Screen.MainMenu.route) {
                        MainMenuScreen(
                            onMovieClick = { id -> navigateToScreen(Screen.MovieDetail.createRoute(id)) }
                        )
                    }

                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            onBackClick = { /* no se usa; top bar global */ },
                            onEditClick = { navigateToScreen(Screen.EditProfile.route) },
                            onLogoutClick = {
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.EditProfile.route) {
                        EditProfileScreen(
                            onSave = { navigateBack() },
                            onBackClick = { navigateBack() } // flecha atrás dentro de la pantalla
                        )
                    }

                    composable(Screen.Friends.route) { FriendsScreen(onBack = navigateBack) }

                    composable(Screen.List.route) {
                        ListScreen(
                            onMovieClick = { movieId -> navigateToScreen(Screen.MovieDetail.createRoute(movieId)) }
                        )
                    }

                    composable(Screen.Reviews.route) { ReviewsScreen(onBack = navigateBack) }

                    composable(Screen.Favorites.route) {
                        FavoritesScreen(
                            onBack = navigateBack,
                            onMovieClick = { movieId -> navigateToScreen(Screen.MovieDetail.createRoute(movieId)) }
                        )
                    }

                    composable(Screen.Settings.route) { SettingsScreen(onBack = navigateBack) }

                    // ---------- DETALLE ----------
                    composable(
                        route = Screen.MovieDetail.route,
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                        MovieDetailRoute(movieId = movieId, onBack = navigateBack)
                    }
                    composable(
                        route = Screen.Synopsis.route,
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                        SynopsisScreen(movieId = movieId, onBackClick = navigateBack)
                    }
                }
            }

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
