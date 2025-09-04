package com.example.ratingroom.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.*
import com.example.ratingroom.ui.utils.*

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = { onLoginClick(uiState.username, uiState.password) },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick,
        modifier = modifier
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUIState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    // Colores desde tu paleta MD3 (se ve igual pero ahora responde al tema claro/oscuro)
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val primary = MaterialTheme.colorScheme.primary

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AuthCard {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logoratingroom),
                    contentDescription = stringResource(id = R.string.content_desc_logo),
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Título
                Text(
                    text = stringResource(id = R.string.login_title),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = stringResource(id = R.string.login_subtitle),
                    fontSize = 14.sp,
                    color = onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campo de email
                EmailField(
                    value = uiState.username,
                    onValueChange = onEmailChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de contraseña
                PasswordField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    showForgotPassword = true,
                    onForgotPasswordClick = onForgotPasswordClick
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de iniciar sesión
                CustomButton(
                    text = stringResource(id = R.string.login_button),
                    onClick = onLoginClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Enlace de registro
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.login_no_account),
                        fontSize = 14.sp,
                        color = onSurfaceVariant
                    )
                    TextButton(
                        onClick = onRegisterClick
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_register_link),
                            fontSize = 14.sp,
                            color = primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Texto adicional
                Text(
                    text = stringResource(id = R.string.login_demo_text),
                    fontSize = 12.sp,
                    color = onSurfaceVariant,
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
        LoginScreenContent(
            uiState = LoginUIState(
                username = "",
                password = ""
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {},
            onForgotPasswordClick = {}
        )
    }
}
