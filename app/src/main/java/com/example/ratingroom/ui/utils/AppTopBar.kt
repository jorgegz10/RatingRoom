package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    endIcon: ImageVector? = null,
    onEndClick: (() -> Unit)? = null
) {
    val cs = MaterialTheme.colorScheme
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            colors = IconButtonDefaults.iconButtonColors(contentColor = cs.onPrimary)
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
        }

        Text(
            text = title,
            color = cs.onPrimary,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (endIcon != null && onEndClick != null) {
            IconButton(
                onClick = onEndClick,
                colors = IconButtonDefaults.iconButtonColors(contentColor = cs.onPrimary)
            ) {
                Icon(endIcon, contentDescription = null)
            }
        }
    }
}
