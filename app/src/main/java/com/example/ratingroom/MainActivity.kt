package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.ratingroom.ui.screens.*
import com.example.ratingroom.ui.theme.RatingRoomTheme

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

@Composable
fun RatingRoomApp() {
    var currentScreen by remember { mutableStateOf("login") }

    when (currentScreen) {
        "login" -> {
            LoginScreen(
                onLoginClick = { email, password ->
                    // Aquí normalmente iría lógica de autenticación
                    println("Login: $email")
                    currentScreen = "mainMenu"
                },
                onRegisterClick = {
                    currentScreen = "register"
                },
                onForgotPasswordClick = {
                    println("Recuperar contraseña")
                }
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
                }
            )
        }

        "mainMenu" -> {
            MainMenuScreen(
                onLogout = {
                    currentScreen = "login"
                },
                onProfileClick = {
                    currentScreen = "editProfile"
                }
            )
        }

        "editProfile" -> {
            EditProfileScreen(
                onBackClick = {
                    currentScreen = "mainMenu"
                }
            )
        }
    }
}
