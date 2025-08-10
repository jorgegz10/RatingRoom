package com.example.ratingroom.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.*
import com.example.ratingroom.ui.utils.*

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _ -> },
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var favoriteGenre by remember { mutableStateOf("") }
    var birthYear by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            AuthCard {
                // Botón de retroceso y logo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = colorResource(id = R.color.medium_gray)
                        )
                    }
                    
                    Image(
                        painter = painterResource(id = R.drawable.logoratingroom),
                        contentDescription = "Logo",
                        modifier = Modifier.size(60.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = "Crear Cuenta",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = "Únete a Ratingroom y descubre películas increíbles",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.medium_gray),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de nombre completo
                TextInputField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Nombre completo",
                    placeholder = "Tu nombre"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de email
                EmailField(
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de contraseña
                PasswordField(
                    value = password,
                    onValueChange = { password = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de confirmar contraseña
                PasswordField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirmar contraseña",
                    placeholder = "Confirma tu contraseña"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de género favorito (opcional)
                TextInputField(
                    value = favoriteGenre,
                    onValueChange = { favoriteGenre = it },
                    label = "Género favorito (opcional)",
                    placeholder = "Acción, Drama, Comedia..."
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de año de nacimiento (opcional)
                TextInputField(
                    value = birthYear,
                    onValueChange = { birthYear = it },
                    label = "Año de nacimiento (opcional)",
                    placeholder = "1990",
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Checkbox de términos y condiciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = acceptTerms,
                        onCheckedChange = { acceptTerms = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(id = R.color.primary_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Acepto los términos y condiciones",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de registro
                CustomButton(
                    text = "Crear cuenta",
                    onClick = {
                        onRegisterClick(fullName, email, password, confirmPassword, favoriteGenre, birthYear)
                    },
                    backgroundColor = colorResource(id = R.color.primary_blue)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Enlace de login
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes cuenta? ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.medium_gray)
                    )
                    TextButton(
                        onClick = onLoginClick
                    ) {
                        Text(
                            text = "Inicia sesión aquí",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.primary_blue),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Texto adicional
                Text(
                    text = "Únete a nuestra comunidad de amantes del cine",
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
fun RegisterScreenPreview() {
    RatingRoomTheme {
        RegisterScreen()
    }
}