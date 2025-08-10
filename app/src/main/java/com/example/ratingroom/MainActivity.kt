package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ratingroom.ui.screens.*
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*

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
    var currentScreen by remember { mutableStateOf("login") }
    var profileMenuExpanded by remember { mutableStateOf(false) }

    // Determinar si mostrar TopBar
    val showTopBar = currentScreen in listOf("mainMenu", "editProfile")
    
    // Determinar el título del TopBar
    val topBarTitle = when (currentScreen) {
        "mainMenu" -> stringResource(id = R.string.main_menu_title)
        "editProfile" -> stringResource(id = R.string.edit_profile_title)
        else -> ""
    }

    GradientBackground {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    TopAppBar(
                        title = {
                            Text(topBarTitle, color = Color.White)
                        },
                        navigationIcon = {
                            when (currentScreen) {
                                "mainMenu" -> {
                                    IconButton(onClick = { /* Logo */ }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logoratingroom),
                                            contentDescription = stringResource(id = R.string.content_desc_logo),
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                                "editProfile" -> {
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
                                            currentScreen = "editProfile"
                                        },
                                        onLogoutClick = {
                                            profileMenuExpanded = false
                                            currentScreen = "login"
                                        }
                                    )
                                }
                                "editProfile" -> {
                                    IconButton(onClick = { /* Guardar cambios */ }) {
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
            // Contenido de las pantallas
            when (currentScreen) {
                "login" -> {
                    LoginScreen(
                        onLoginClick = { email, password ->
                            println("Login: $email")
                            currentScreen = "mainMenu"
                        },
                        onRegisterClick = {
                            currentScreen = "register"
                        },
                        onForgotPasswordClick = {
                            println("Recuperar contraseña")
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                "register" -> {
                    RegisterScreen(
                        onRegisterClick = { fullName, email, password, confirmPassword, favoriteGenre, birthYear ->
                            println("Registro: $fullName, $email")
                            currentScreen = "login"
                        },
                        onLoginClick = {
                            currentScreen = "login"
                        },
                        onBackClick = {
                            currentScreen = "login"
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                "mainMenu" -> {
                    MainMenuScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                "editProfile" -> {
                    EditProfileScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
