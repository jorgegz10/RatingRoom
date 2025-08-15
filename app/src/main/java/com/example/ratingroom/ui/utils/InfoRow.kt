package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(
    icon: ImageVector,
    text: String
) {
    val cs = MaterialTheme.colorScheme
    Row {
        Icon(icon, contentDescription = null, tint = cs.onSurfaceVariant)
        Spacer(Modifier.width(6.dp))
        Text(text, color = cs.onSurfaceVariant)
    }
}
