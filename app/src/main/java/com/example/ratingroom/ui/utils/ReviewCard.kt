package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ReviewCard(
    title: String,
    rating: Int,                // 0..5
    excerpt: String,
    timeAgo: String,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxWidth()) {
        Text(title, fontWeight = FontWeight.SemiBold, color = cs.onSurface)
        Spacer(Modifier.height(4.dp))
        Row {
            repeat(5) { i ->
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = if (i < rating) cs.tertiary else cs.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = excerpt,
            color = cs.onSurfaceVariant,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(6.dp))
        Text(timeAgo, color = cs.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
    }
}
