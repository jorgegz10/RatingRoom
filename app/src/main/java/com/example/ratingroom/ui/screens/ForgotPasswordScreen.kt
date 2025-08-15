package com.example.ratingroom.ui.screens

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.*
import com.example.ratingroom.ui.utils.*

@Composable
fun ForgotPasswordScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onSendRecoveryClick: (String) -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showHowItWorks by remember { mutableStateOf(false) }
    
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
                    IconButton(
                        onClick = onBackToLoginClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_desc_back),
                            tint = colorResource(id = R.color.medium_gray)
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
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = stringResource(id = R.string.forgot_password_subtitle),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.medium_gray),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de email
                EmailField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = stringResource(id = R.string.forgot_password_email_label),
                    placeholder = stringResource(id = R.string.forgot_password_email_placeholder)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ¿Cómo funciona?
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (showHowItWorks) 
                            colorResource(id = R.color.light_gray) 
                        else 
                            colorResource(id = R.color.light_gray).copy(alpha = 0.5f)
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
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = colorResource(id = R.color.primary_blue),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.forgot_password_how_it_works),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.black)
                            )
                        }
                        
                        if (showHowItWorks) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.forgot_password_explanation),
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.medium_gray),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
                
                // Toggle para mostrar/ocultar explicación
                TextButton(
                    onClick = { showHowItWorks = !showHowItWorks },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (showHowItWorks) "Ocultar" else "Mostrar detalles",
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.primary_blue)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de enviar
                CustomButton(
                    text = stringResource(id = R.string.forgot_password_send_button),
                    onClick = { onSendRecoveryClick(email) },
                    backgroundColor = colorResource(id = R.color.black),
                    textColor = colorResource(id = R.color.white),
                    modifier = Modifier.height(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Link para volver al login - CORREGIDO
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.forgot_password_remember),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.medium_gray),
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        onClick = onBackToLoginClick
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgot_password_back_to_login),
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.primary_blue),
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
                    color = colorResource(id = R.color.medium_gray),
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
        ForgotPasswordScreen(
            email = "",
            onEmailChange = {},
            onSendRecoveryClick = {},
            onBackToLoginClick = {}
        )
    }
}