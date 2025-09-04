package com.example.ratingroom.ui.screens.register

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.*
import com.example.ratingroom.ui.utils.*

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _ -> },
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    RegisterScreenContent(
        uiState = uiState,
        onFullNameChange = viewModel::onFullNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onFavoriteGenreChange = viewModel::onFavoriteGenreChange,
        onBirthYearChange = viewModel::onBirthYearChange,
        onAcceptTermsChange = viewModel::onAcceptTermsChange,
        onRegisterClick = {
            onRegisterClick(
                uiState.fullName,
                uiState.email,
                uiState.password,
                uiState.confirmPassword,
                uiState.favoriteGenre,
                uiState.birthYear
            )
        },
        onLoginClick = onLoginClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun RegisterScreenContent(
    uiState: RegisterUIState,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onFavoriteGenreChange: (String) -> Unit,
    onBirthYearChange: (String) -> Unit,
    onAcceptTermsChange: (Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val cs = MaterialTheme.colorScheme

    GradientBackground { // ✅ fondo azul degradado
        Column(
            modifier = modifier
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
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_desc_back),
                            tint = cs.onSurfaceVariant
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logoratingroom),
                        contentDescription = stringResource(id = R.string.content_desc_logo),
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = stringResource(id = R.string.register_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = cs.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = stringResource(id = R.string.register_subtitle),
                    fontSize = 14.sp,
                    color = cs.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de nombre completo
                TextInputField(
                    value = uiState.fullName,
                    onValueChange = onFullNameChange,
                    label = stringResource(id = R.string.field_full_name),
                    placeholder = stringResource(id = R.string.field_full_name_placeholder)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de email
                EmailField(
                    value = uiState.email,
                    onValueChange = onEmailChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de contraseña
                PasswordField(
                    value = uiState.password,
                    onValueChange = onPasswordChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de confirmar contraseña
                PasswordField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = stringResource(id = R.string.field_confirm_password),
                    placeholder = stringResource(id = R.string.field_confirm_password_placeholder)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de género favorito (opcional)
                TextInputField(
                    value = uiState.favoriteGenre,
                    onValueChange = onFavoriteGenreChange,
                    label = stringResource(id = R.string.field_favorite_genre),
                    placeholder = stringResource(id = R.string.field_favorite_genre_placeholder)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de año de nacimiento (opcional)
                TextInputField(
                    value = uiState.birthYear,
                    onValueChange = onBirthYearChange,
                    label = stringResource(id = R.string.field_birth_year),
                    placeholder = stringResource(id = R.string.field_birth_year_placeholder),
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Checkbox de términos y condiciones (usa colores del tema)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = uiState.acceptTerms,
                    onCheckedChange = onAcceptTermsChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = cs.primary,
                            uncheckedColor = cs.onSurfaceVariant,
                            checkmarkColor = cs.onPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.register_terms),
                        fontSize = 14.sp,
                        color = cs.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de registro
                CustomButton(
                    text = stringResource(id = R.string.register_button),
                    onClick = onRegisterClick,
                    backgroundColor = cs.primary,
                    textColor = cs.onPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Enlace de login
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.register_has_account),
                        fontSize = 14.sp,
                        color = cs.onSurfaceVariant
                    )
                    TextButton(onClick = onLoginClick) {
                        Text(
                            text = stringResource(id = R.string.register_login_link),
                            fontSize = 14.sp,
                            color = cs.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Texto adicional
                Text(
                    text = stringResource(id = R.string.register_community_text),
                    fontSize = 12.sp,
                    color = cs.onSurfaceVariant,
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
        RegisterScreenContent(
            uiState = RegisterUIState(
                fullName = "",
                email = "",
                password = "",
                confirmPassword = "",
                favoriteGenre = "",
                birthYear = "",
                acceptTerms = false
            ),
            onFullNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onFavoriteGenreChange = {},
            onBirthYearChange = {},
            onAcceptTermsChange = {},
            onRegisterClick = {},
            onLoginClick = {},
            onBackClick = {}
        )
    }
}
