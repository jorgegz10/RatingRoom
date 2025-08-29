package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StarRating(
    rating: Double,
    maxRating: Int = 5,
    starSize: Dp = 16.dp,
    showText: Boolean = true,
    modifier: Modifier = Modifier
) {
    val c = MaterialTheme.colorScheme
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(maxRating) { index ->
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) c.tertiary else c.onSurfaceVariant,
                modifier = Modifier.size(starSize)
            )
        }
        if (showText) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$rating/$maxRating",
                fontSize = 12.sp,
                color = c.onSurfaceVariant
            )
        }
    }
}
