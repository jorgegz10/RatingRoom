package com.example.ratingroom.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserAvatar(
    name: String,
    isOnline: Boolean = false,
    size: Dp = 40.dp,
    backgroundColor: Color = Color(0xFF2196F3),
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            shape = CircleShape,
            color = backgroundColor,
            modifier = Modifier.size(size)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.split(" ").map { it.first() }.joinToString(""),
                    fontSize = (size.value * 0.35).sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
        
        if (isOnline) {
            Box(
                modifier = Modifier
                    .size(size * 0.25f)
                    .background(Color.Green, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}