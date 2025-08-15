package com.example.ratingroom.ui.utils

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun InfoChip(
    text: String,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        leadingIcon = if (icon != null) {
            { Icon(icon, contentDescription = null) }
        } else null
    )
}
