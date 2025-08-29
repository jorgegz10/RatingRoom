package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyActivityState(
    modifier: Modifier = Modifier
) {
    val c = MaterialTheme.colorScheme
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Timeline,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = c.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No hay actividad reciente",
            fontSize = 18.sp,
            color = c.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Las reseñas y actividades de tus amigos aparecerán aquí",
            fontSize = 14.sp,
            color = c.onSurfaceVariant
        )
    }
}

@Composable
fun EmptyFriendsState(
    tabIndex: Int,
    modifier: Modifier = Modifier
) {
    val (message, icon) = when (tabIndex) {
        1 -> Pair("No sigues a nadie", Icons.Default.PersonAdd)
        2 -> Pair("No tienes seguidores", Icons.Default.People)
        3 -> Pair("Descubre nuevos amigos", Icons.Default.Search)
        else -> Pair("No tienes amigos aún", Icons.Default.Group)
    }

    val c = MaterialTheme.colorScheme
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = c.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            color = c.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "¡Agrega tu primer amigo!",
            fontSize = 14.sp,
            color = c.onSurfaceVariant
        )
    }
}
