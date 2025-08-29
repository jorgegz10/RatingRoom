package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SocialActionButtons(
    onLike: () -> Unit,
    onComment: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = MaterialTheme.colorScheme
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = onLike) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Me gusta",
                tint = c.error
            )
        }
        IconButton(onClick = onComment) {
            Icon(
                imageVector = Icons.Default.Comment,
                contentDescription = "Comentar",
                tint = c.onSurfaceVariant
            )
        }
    }
}
