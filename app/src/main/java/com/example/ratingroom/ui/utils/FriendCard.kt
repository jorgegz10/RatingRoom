package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.data.models.Friend

@Composable
fun FriendCard(
    friend: Friend,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                name = friend.name,
                isOnline = friend.isOnline,
                size = 50.dp,
                backgroundColor = Color.LightGray,
                textColor = Color.DarkGray
            )

            Spacer(modifier = Modifier.width(12.dp))

            FriendInfo(
                friend = friend,
                modifier = Modifier.weight(1f)
            )

            FriendActions(
                onMessage = { onAction("message") },
                onViewProfile = { onAction("view_profile") }
            )
        }
    }
}

@Composable
fun FriendInfo(
    friend: Friend,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = friend.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = friend.username,
            fontSize = 14.sp,
            color = Color.Gray
        )
        
        Text(
            text = if (friend.isOnline) "En línea" else "Visto por última vez ${friend.lastSeen}",
            fontSize = 12.sp,
            color = if (friend.isOnline) Color.Green else Color.Gray
        )
        
        if (friend.mutualFriends > 0) {
            Text(
                text = "${friend.mutualFriends} amigos en común",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun FriendActions(
    onMessage: () -> Unit,
    onViewProfile: () -> Unit
) {
    Column {
        IconButton(onClick = onMessage) {
            Icon(
                Icons.Default.Message,
                contentDescription = "Mensaje",
                tint = Color.White
            )
        }
        
        IconButton(onClick = onViewProfile) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Ver perfil",
                tint = Color.Gray
            )
        }
    }
}