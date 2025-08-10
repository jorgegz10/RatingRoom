package com.example.ratingroom.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ratingroom.R

@Composable
fun ProfileMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(32.dp)
                .background(Color.White, CircleShape)
                .clickable { onExpandedChange(true) },
            contentAlignment = Alignment.Center
        ) {}

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.main_menu_profile)) },
                onClick = onProfileClick
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.main_menu_logout)) },
                onClick = onLogoutClick
            )
        }
    }
}