package com.example.ratingroom.ui.screens.profile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkMode = uiState.isDarkMode.takeIf { uiState.profileData != null } ?: isSystemInDarkTheme()
    
    ProfileScreenContent(
        uiState = uiState,
        isDarkMode = isDarkMode,
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDarkModeChange = viewModel::onDarkModeChange,
        modifier = modifier
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileUIState,
    isDarkMode: Boolean,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            AppTopBar(
                config = TopBarConfig(
                    title = "Mi Perfil",
                    showBackButton = true,
                    onBackClick = onBackClick
                )
            )

            Spacer(Modifier.height(8.dp))

            Surface(
                color = cs.surface,
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(Modifier.fillMaxSize().padding(12.dp)) {

                    if (uiState.isLoading) {
                        // Loading state
                        Box(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        uiState.profileData?.let { profileData ->
                            ProfileHeader(
                                profileData = profileData,
                                colorScheme = cs
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    uiState.profileData?.let { profileData ->
                        ProfileMetrics(
                            reviewsCount = profileData.reviewsCount,
                            averageRating = profileData.averageRating,
                            colorScheme = cs
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    ProfileSettings(
                        isDarkMode = isDarkMode,
                        onDarkModeChange = onDarkModeChange,
                        colorScheme = cs
                    )

                    Spacer(Modifier.height(12.dp))

                    // Componente sin remember - solo UI
                    RecentReviews(
                        colorScheme = cs
                    )
                }
            }
        }
    }
}

// Componente pequeño SIN remember
@Composable
fun ProfileHeader(
    profileData: ProfileData,
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier
) {
    SectionCard(title = "") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarInitials(initials = profileData.name.take(2).uppercase())
            Spacer(Modifier.height(12.dp))
            Text(
                text = profileData.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorScheme.onSurface
            )
            Text(
                text = profileData.email,
                fontSize = 14.sp,
                color = colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            InfoRow(
                icon = Icons.Filled.CalendarMonth,
                text = "Miembro desde ${profileData.memberSince}"
            )
            Spacer(Modifier.height(8.dp))
            InfoChip(
                text = "Género favorito: ${profileData.favoriteGenre}",
                icon = Icons.Filled.Category
            )
        }
    }
}

// Componente pequeño SIN remember
@Composable
fun ProfileMetrics(
    reviewsCount: Int,
    averageRating: Double,
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MetricCard(
            icon = Icons.Filled.ChatBubbleOutline,
            number = reviewsCount.toString(),
            label = "Reseñas",
            modifier = Modifier.weight(1f)
        )
        MetricCard(
            icon = Icons.Filled.Star,
            number = String.format("%.1f", averageRating),
            label = "Promedio",
            iconTint = colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
    }
}

// Componente pequeño SIN remember
@Composable
fun ProfileSettings(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier
) {
    SectionCard(title = "Configuración") {
        // Toggle de modo oscuro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                    contentDescription = "Modo oscuro",
                    tint = colorScheme.onSurface
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "Modo oscuro",
                    color = colorScheme.onSurface,
                    fontSize = 16.sp
                )
            }
            Switch(
                checked = isDarkMode,
                onCheckedChange = onDarkModeChange
            )
        }
        
        HorizontalDivider()  // Changed from Divider() to HorizontalDivider()
        
        SettingsList(
            items = listOf(
                SettingsItem("edit", "Editar perfil", Icons.Filled.Person),
                SettingsItem("privacy", "Configuración de privacidad", Icons.Filled.Lock),
                SettingsItem("notif", "Notificaciones", Icons.Filled.Notifications),
                SettingsItem("logout", "Cerrar sesión", Icons.Filled.Logout, tint = colorScheme.error)
            ),
            onItemClick = { /* manejar id */ }
        )
    }
}

// Componente pequeño SIN remember
@Composable
fun RecentReviews(
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier
) {
    SectionCard(title = "Mis Reseñas Recientes") {
        ReviewCard(
            title = "Inception",
            rating = 5,
            excerpt = "Una película increíble que te hace pensar. Los efectos visuales son espectaculares y la historia es muy original.",
            timeAgo = "Hace 3 días"
        )
        HorizontalDivider()  // Changed from Divider() to HorizontalDivider()
        ReviewCard(
            title = "The Matrix",
            rating = 4,
            excerpt = "Clásico de la ciencia ficción. Revolucionó el género y sigue vigente.",
            timeAgo = "Hace 1 semana"
        )
        HorizontalDivider()  // Changed from Divider() to HorizontalDivider()
        ReviewCard(
            title = "Interstellar",
            rating = 5,
            excerpt = "Obra maestra: ciencia, emoción e imágenes se combinan perfectamente.",
            timeAgo = "Hace 2 semanas"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    RatingRoomTheme {
        ProfileScreenContent(
            uiState = ProfileUIState(
                profileData = ProfileData(
                    name = "Pedro",
                    email = "pedro@correo.com",
                    memberSince = "Enero 2024",
                    favoriteGenre = "Sci-Fi",
                    reviewsCount = 3,
                    averageRating = 4.7
                ),
                isDarkMode = false,
                isLoading = false
            ),
            isDarkMode = false,
            onBackClick = {},
            onEditClick = {},
            onDarkModeChange = {}
        )
    }
}
