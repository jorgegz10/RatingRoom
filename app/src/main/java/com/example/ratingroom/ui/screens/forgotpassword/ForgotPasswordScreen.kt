package com.example.ratingroom.ui.screens.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.*
import com.example.ratingroom.ui.utils.*

@Composable
fun ForgotPasswordScreen(
    onSendRecoveryClick: (String) -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    ForgotPasswordScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onSendRecoveryClick = { onSendRecoveryClick(uiState.email) },
        onBackToLoginClick = onBackToLoginClick,
        onShowHowItWorksChange = viewModel::onShowHowItWorksChange,
        modifier = modifier
    )
}

@Composable
fun ForgotPasswordScreenContent(
    uiState: ForgotPasswordUIState,
    onEmailChange: (String) -> Unit,
    onSendRecoveryClick: () -> Unit,
    onBackToLoginClick: () -> Unit,
    onShowHowItWorksChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AuthCard {
                // Botón de retroceso y logo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackToLoginClick) {
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
                    text = stringResource(id = R.string.forgot_password_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = cs.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = stringResource(id = R.string.forgot_password_subtitle),
                    fontSize = 14.sp,
                    color = cs.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de email
                EmailField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = stringResource(id = R.string.forgot_password_email_label),
                    placeholder = stringResource(id = R.string.forgot_password_email_placeholder)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ¿Cómo funciona?
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = cs.surfaceVariant.copy(
                            alpha = if (uiState.showHowItWorks) 1f else 0.5f
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = cs.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.forgot_password_how_it_works),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = cs.onSurface
                            )
                        }

                        if (uiState.showHowItWorks) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.forgot_password_explanation),
                                fontSize = 12.sp,
                                color = cs.onSurfaceVariant,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                // Toggle mostrar/ocultar explicación
                TextButton(
                    onClick = { onShowHowItWorksChange(!uiState.showHowItWorks) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (uiState.showHowItWorks) "Ocultar" else "Mostrar detalles",
                        fontSize = 12.sp,
                        color = cs.primary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de enviar
                CustomButton(
                    text = stringResource(id = R.string.forgot_password_send_button),
                    onClick = onSendRecoveryClick,
                    backgroundColor = cs.primary,
                    textColor = cs.onPrimary,
                    modifier = Modifier.height(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Link para volver al login
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.forgot_password_remember),
                        fontSize = 14.sp,
                        color = cs.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = onBackToLoginClick) {
                        Text(
                            text = stringResource(id = R.string.forgot_password_back_to_login),
                            fontSize = 14.sp,
                            color = cs.primary,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Texto demo
                Text(
                    text = stringResource(id = R.string.forgot_password_demo_text),
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
fun ForgotPasswordScreenPreview() {
    RatingRoomTheme {
        ForgotPasswordScreenContent(
            uiState = ForgotPasswordUIState(
                email = "",
                showHowItWorks = false
            ),
            onEmailChange = {},
            onSendRecoveryClick = {},
            onBackToLoginClick = {},
            onShowHowItWorksChange = {}
        )
    }
}
