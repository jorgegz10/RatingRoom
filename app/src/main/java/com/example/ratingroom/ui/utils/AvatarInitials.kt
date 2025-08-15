package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AvatarInitials(
    initials: String,
    size: androidx.compose.ui.unit.Dp = 80.dp
) {
    val cs = MaterialTheme.colorScheme
    Surface(
        color = cs.surfaceVariant,
        shape = CircleShape,
        modifier = Modifier.size(size).clip(CircleShape)
    ) {
        Box(Modifier.size(size), contentAlignment = Alignment.Center) {
            Text(initials, color = cs.onSurfaceVariant, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
