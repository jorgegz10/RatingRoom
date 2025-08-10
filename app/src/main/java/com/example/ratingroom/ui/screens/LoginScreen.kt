package com.example.ratingroom.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.*
import com.example.ratingroom.ui.utils.*

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AuthCard {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logoratingroom),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Título
                Text(
                    text = "Ratingroom",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = "Inicia sesión para descubrir y calificar películas",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.medium_gray),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campo de email
                EmailField(
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de contraseña
                PasswordField(
                    value = password,
                    onValueChange = { password = it },
                    showForgotPassword = true,
                    onForgotPasswordClick = onForgotPasswordClick
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de iniciar sesión
                CustomButton(
                    text = "Iniciar sesión",
                    onClick = { onLoginClick(email, password) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Enlace de registro
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿No tienes cuenta? ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.medium_gray)
                    )
                    TextButton(
                        onClick = onRegisterClick
                    ) {
                        Text(
                            text = "Regístrate aquí",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.primary_blue),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Texto adicional
                Text(
                    text = "Disfruta con cualquier email y contraseña",
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.medium_gray),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RatingRoomTheme {
        LoginScreen()
    }
}