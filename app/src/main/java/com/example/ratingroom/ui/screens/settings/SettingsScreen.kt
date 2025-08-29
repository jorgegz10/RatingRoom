package com.example.ratingroom.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.AppTopBar
import com.example.ratingroom.ui.utils.TopBarConfig

@Composable
fun SettingsScreen(
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    SettingsScreenContent(
        uiState = uiState,
        onBack = onBack,
        onNotificationsChange = viewModel::onNotificationsChange,
        onDarkModeChange = viewModel::onDarkModeChange,
        onLanguageChange = viewModel::onLanguageChange,
        modifier = modifier
    )
}

@Composable
fun SettingsScreenContent(
    uiState: SettingsUIState,
    onBack: () -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopBar(
                TopBarConfig(
                    title = "Configuración",
                    showBackButton = true,
                    onBackClick = onBack
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SettingItem(
                    title = "Notificaciones",
                    subtitle = "Recibir notificaciones de la app",
                    icon = Icons.Default.Notifications,
                    trailing = {
                        Switch(
                            checked = uiState.notificationsEnabled,
                            onCheckedChange = onNotificationsChange
                        )
                    }
                )
            }
            
            item {
                SettingItem(
                    title = "Modo Oscuro",
                    subtitle = "Cambiar tema de la aplicación",
                    icon = Icons.Default.DarkMode,
                    trailing = {
                        Switch(
                            checked = uiState.darkModeEnabled,
                            onCheckedChange = onDarkModeChange
                        )
                    }
                )
            }
            
            item {
                SettingItem(
                    title = "Idioma",
                    subtitle = "Español",
                    icon = Icons.Default.Language,
                    onClick = { /* TODO: Implementar selector de idioma */ }
                )
            }
            
            item {
                SettingItem(
                    title = "Privacidad",
                    subtitle = "Configurar privacidad de perfil",
                    icon = Icons.Default.Lock,
                    onClick = { /* TODO: Navegar a pantalla de privacidad */ }
                )
            }
            
            item {
                SettingItem(
                    title = "Acerca de",
                    subtitle = "Información de la aplicación",
                    icon = Icons.Default.Info,
                    onClick = { /* TODO: Mostrar información de la app */ }
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick ?: {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            trailing?.invoke()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    RatingRoomTheme {
        SettingsScreenContent(
            uiState = SettingsUIState(
                notificationsEnabled = true,
                darkModeEnabled = false,
                selectedLanguage = "es"
            ),
            onBack = {},
            onNotificationsChange = {},
            onDarkModeChange = {},
            onLanguageChange = {}
        )
    }
}