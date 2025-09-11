package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AvatarInitials(
    initials: String,
    size: androidx.compose.ui.unit.Dp = 80.dp,
    imageUrl: String? = null
) {
    val cs = MaterialTheme.colorScheme
    Surface(
        color = cs.surfaceVariant,
        shape = CircleShape,
        modifier = Modifier.size(size).clip(CircleShape)
    ) {
        Box(Modifier.size(size), contentAlignment = Alignment.Center) {
            if (imageUrl != null) {
                println("AvatarInitials: Mostrando imagen con URL: $imageUrl")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onLoading = { println("AvatarInitials: Cargando imagen...") },
                    onSuccess = { println("AvatarInitials: Imagen cargada exitosamente") },
                    onError = { println("AvatarInitials: Error al cargar imagen: $imageUrl") }
                )
            } else {
                println("AvatarInitials: Mostrando iniciales: $initials")
                Text(initials, color = cs.onSurfaceVariant, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
