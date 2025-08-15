package com.example.ratingroom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.ui.utils.*
import com.example.ratingroom.data.repository.FriendsRepository
import com.example.ratingroom.data.models.Friend
import com.example.ratingroom.data.models.FriendActivity
import com.example.ratingroom.data.models.FriendshipType
import com.example.ratingroom.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onFriendAction: (Friend, String) -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header con degradado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A2E),
                            Color(0xFF16213E)
                        )
                    )
                )
        ) {
            Column {
                // Barra superior con botón atrás y título
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "Amigos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    
                    IconButton(
                        onClick = { /* Acción de búsqueda */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }
                }
                
                // Estadísticas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        number = "3",
                        label = "Siguiendo",
                        modifier = Modifier.weight(1f)
                    )
                    
                    StatItem(
                        number = "4",
                        label = "Seguidores",
                        modifier = Modifier.weight(1f)
                    )
                    
                    StatItem(
                        number = "2",
                        label = "Mutuos",
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        
        // Contenido principal con fondo blanco
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Usar SearchBar de utils (que SÍ es fijo)
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    placeholder = "Buscar amigos...",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tabs horizontales
                FriendsTabRow(
                    selectedTab = selectedTab,
                    onTabSelected = onTabSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido según la pestaña seleccionada
                when (selectedTab) {
                    0 -> {
                        ActivityTab(
                            searchQuery = searchQuery,
                            onFriendAction = onFriendAction,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        FriendsListTab(
                            tabIndex = selectedTab,
                            searchQuery = searchQuery,
                            onFriendAction = onFriendAction,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(
    number: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun FriendsTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Actividad", "Siguiendo", "Seguidores", "Descubrir")
    
    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = Color(0xFF2196F3)
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        color = if (selectedTab == index) Color(0xFF2196F3) else Color.Gray,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            )
        }
    }
}

@Composable
fun ActivityTab(
    searchQuery: String,
    onFriendAction: (Friend, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val friendsActivity = remember {
        FriendsRepository.getFriendsActivity()
    }
    
    val filteredActivity = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            friendsActivity
        } else {
            friendsActivity.filter { 
                it.friend.name.contains(searchQuery, ignoreCase = true) ||
                it.movie.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        if (filteredActivity.isEmpty()) {
            item {
                EmptyActivityState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        } else {
            items(filteredActivity) { activity ->
                FriendActivityCard(
                    activity = activity,
                    onAction = { action -> onFriendAction(activity.friend, action) }
                )
            }
        }
    }
}

@Composable
fun FriendsListTab(
    tabIndex: Int,
    searchQuery: String,
    onFriendAction: (Friend, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val friendsList = remember(tabIndex, searchQuery) {
        val allFriends = when (tabIndex) {
            1 -> FriendsRepository.getFollowing()
            2 -> FriendsRepository.getFollowers()
            3 -> FriendsRepository.getAllFriends()
            else -> emptyList()
        }
        
        if (searchQuery.isBlank()) {
            allFriends
        } else {
            FriendsRepository.searchFriends(searchQuery).filter { friend ->
                when (tabIndex) {
                    1 -> friend.relationshipType == FriendshipType.FOLLOWING || friend.relationshipType == FriendshipType.MUTUAL
                    2 -> friend.relationshipType == FriendshipType.FOLLOWER || friend.relationshipType == FriendshipType.MUTUAL
                    3 -> true
                    else -> false
                }
            }
        }
    }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        if (friendsList.isEmpty()) {
            item {
                EmptyFriendsState(
                    tabIndex = tabIndex,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        } else {
            items(friendsList) { friend ->
                FriendCard(
                    friend = friend,
                    onAction = { action -> onFriendAction(friend, action) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFriendsScreen() {
    FriendsScreen(
        searchQuery = "",
        onSearchQueryChange = {},
        selectedTab = 0,
        onTabSelected = {},
        onFriendAction = { _, _ -> },
        onBackClick = {}
    )
}

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
            // Avatar
            Box {
                Surface(
                    shape = CircleShape,
                    color = Color.LightGray,
                    modifier = Modifier.size(50.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = friend.name.first().toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }
                }
                
                // Indicador de estado online
                if (friend.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color.Green, CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información del amigo
            Column(
                modifier = Modifier.weight(1f)
            ) {
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
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (friend.isOnline) {
                        Text(
                            text = stringResource(id = R.string.friends_online),
                            fontSize = 12.sp,
                            color = Color.Green
                        )
                    } else {
                        Text(
                            text = "${stringResource(id = R.string.friends_last_seen)} ${friend.lastSeen}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                if (friend.mutualFriends > 0) {
                    Text(
                        text = "${friend.mutualFriends} ${stringResource(id = R.string.friends_mutual_friends)}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Botones de acción
            Column {
                when (friend.relationshipType) {
                    FriendshipType.FRIEND, FriendshipType.MUTUAL -> {
                        IconButton(
                            onClick = { onAction("message") }
                        ) {
                            Icon(
                                Icons.Default.Message,
                                contentDescription = stringResource(id = R.string.friends_message),
                                tint = Color.White
                            )
                        }
                    }
                    FriendshipType.FOLLOWING -> {
                        IconButton(
                            onClick = { onAction("unfollow") }
                        ) {
                            Icon(
                                Icons.Default.PersonRemove,
                                contentDescription = stringResource(id = R.string.friends_unfollow),
                                tint = Color.Red
                            )
                        }
                    }
                    FriendshipType.FOLLOWER -> {
                        IconButton(
                            onClick = { onAction("follow_back") }
                        ) {
                            Icon(
                                Icons.Default.PersonAdd,
                                contentDescription = stringResource(id = R.string.friends_follow),
                                tint = Color.Green
                            )
                        }
                    }
                    FriendshipType.NONE -> {
                        IconButton(
                            onClick = { onAction("add_friend") }
                        ) {
                            Icon(
                                Icons.Default.PersonAdd,
                                contentDescription = stringResource(id = R.string.friends_add_friend),
                                tint = Color.White
                            )
                        }
                    }
                }
                
                IconButton(
                    onClick = { onAction("view_profile") }
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.friends_view_profile),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyFriendsState(
    tabIndex: Int,
    modifier: Modifier = Modifier
) {
    val (message, icon) = when (tabIndex) {
        0 -> Pair(stringResource(id = R.string.friends_no_friends), Icons.Default.Group)
        1 -> Pair(stringResource(id = R.string.friends_no_following), Icons.Default.PersonAdd)
        2 -> Pair(stringResource(id = R.string.friends_no_followers), Icons.Default.People)
        else -> Pair("", Icons.Default.Group)
    }
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.friends_add_first_friend),
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}