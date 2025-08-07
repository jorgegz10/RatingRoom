package com.example.ratingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.ratingroom.ui.screens.LoginScreen
import com.example.ratingroom.ui.screens.RegisterScreen
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
                    // TODO: Implementar lógica de autenticación
                    println("Login: $email")
                },
                onRegisterClick = {
                    currentScreen = "register"
                },
                onForgotPasswordClick = {
                    // TODO: Implementar recuperación de contraseña
                    println("Recuperar contraseña")
                }
            )
        }
        "register" -> {
            RegisterScreen(
                onRegisterClick = { fullName, email, password, confirmPassword, favoriteGenre, birthYear ->
                    // TODO: Implementar lógica de registro
                    println("Registro: $fullName, $email")
                    currentScreen = "login" // Volver al login después del registro
                },
                onLoginClick = {
                    currentScreen = "login"
                },
                onBackClick = {
                    currentScreen = "login"
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingRoomAppPreview() {
    RatingRoomTheme {
        RatingRoomApp()
    }
}