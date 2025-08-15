package com.example.ratingroom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*

@Composable
fun ProfileScreen(
    name: String,
    email: String,
    memberSince: String,
    favoriteGenre: String,
    reviewsCount: Int,
    averageRating: Double,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
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
                title = "Mi Perfil",
                onBack = onBackClick,
                endIcon = Icons.Filled.Edit,
                onEndClick = onEditClick
            )

            Spacer(Modifier.height(8.dp))

            Surface(
                color = cs.surface,
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(Modifier.fillMaxSize().padding(12.dp)) {

                    SectionCard(title = "") {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AvatarInitials(initials = name.take(2).uppercase())
                            Spacer(Modifier.height(12.dp))
                            Text(name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = cs.onSurface)
                            Text(email, fontSize = 14.sp, color = cs.onSurfaceVariant)
                            Spacer(Modifier.height(12.dp))
                            InfoRow(icon = Icons.Filled.CalendarMonth, text = "Miembro desde $memberSince")
                            Spacer(Modifier.height(8.dp))
                            InfoChip(text = "Género favorito: $favoriteGenre", icon = Icons.Filled.Category)
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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
                            iconTint = cs.tertiary,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    SectionCard(title = "Mis Reseñas Recientes") {
                        ReviewCard(
                            title = "Inception",
                            rating = 5,
                            excerpt = "Una película increíble que te hace pensar. Los efectos visuales son espectaculares y la historia es muy original.",
                            timeAgo = "Hace 3 días"
                        )
                        Divider()
                        ReviewCard(
                            title = "The Matrix",
                            rating = 4,
                            excerpt = "Clásico de la ciencia ficción. Revolucionó el género y sigue vigente.",
                            timeAgo = "Hace 1 semana"
                        )
                        Divider()
                        ReviewCard(
                            title = "Interstellar",
                            rating = 5,
                            excerpt = "Obra maestra: ciencia, emoción e imágenes se combinan perfectamente.",
                            timeAgo = "Hace 2 semanas"
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    SectionCard(title = "Configuración") {
                        SettingsList(
                            items = listOf(
                                SettingsItem("edit", "Editar perfil", Icons.Filled.Person),
                                SettingsItem("privacy", "Configuración de privacidad", Icons.Filled.Lock),
                                SettingsItem("notif", "Notificaciones", Icons.Filled.Notifications),
                                SettingsItem("logout", "Cerrar sesión", Icons.Filled.Logout, tint = cs.error)
                            ),
                            onItemClick = { /* manejar id */ }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    RatingRoomTheme {
        ProfileScreen(
            name = "Pedro",
            email = "pedro@correo.com",
            memberSince = "Enero 2024",
            favoriteGenre = "Sci-Fi",
            reviewsCount = 3,
            averageRating = 4.7
        )
    }
}
