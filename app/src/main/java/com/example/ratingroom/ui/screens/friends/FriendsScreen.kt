package com.example.ratingroom.ui.screens.friends

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.ui.utils.*
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.data.repository.FriendsRepository
import com.example.ratingroom.data.models.Friend
import com.example.ratingroom.data.models.FriendActivity
import com.example.ratingroom.data.models.FriendshipType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: FriendsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    FriendsScreenContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onTabSelected = viewModel::onTabSelected,
        onFriendAction = { friend, action -> viewModel.onFriendAction(friend.id, action) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreenContent(
    uiState: FriendsUIState,
    onSearchQueryChange: (String) -> Unit,
    onTabSelected: (Int) -> Unit,
    onFriendAction: (Friend, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header con estadísticas (sin TopBar propia)
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
                .padding(16.dp)
        ) {
            // Solo estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                // Barra de búsqueda
                SearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    placeholder = "Buscar amigos...",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tabs horizontales
                FriendsTabRow(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = onTabSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido según la pestaña seleccionada
                when (uiState.selectedTab) {
                    0 -> {
                        ActivityTab(
                            searchQuery = uiState.searchQuery,
                            onFriendAction = onFriendAction,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        FriendsListTab(
                            tabIndex = uiState.selectedTab,
                            searchQuery = uiState.searchQuery,
                            friends = emptyList(),
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
    friends: List<Friend>,
    onFriendAction: (Friend, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val friendsList = remember(friends, searchQuery) {
        if (searchQuery.isBlank()) {
            friends
        } else {
            friends.filter { friend ->
                friend.name.contains(searchQuery, ignoreCase = true) ||
                friend.username.contains(searchQuery, ignoreCase = true)
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
    RatingRoomTheme {
        FriendsScreenContent(
            uiState = FriendsUIState(
                searchQuery = "",
                selectedTab = 0,
                friends = emptyList(),
                suggestions = emptyList(),
                followers = emptyList()
            ),
            onSearchQueryChange = {},
            onTabSelected = {},
            onFriendAction = { _, _ -> }
        )
    }
}

@Composable
fun FriendCard(
    friend: Friend,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cs.surfaceVariant)
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
                    color = cs.surfaceVariant,
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
                            color = cs.onSurfaceVariant
                        )
                    }
                }

                // Indicador de estado online
                if (friend.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(cs.tertiary, CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información del amigo
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = friend.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = cs.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = friend.username,
                    fontSize = 14.sp,
                    color = cs.onSurfaceVariant
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (friend.isOnline) {
                        Text(
                            text = "En línea",
                            fontSize = 12.sp,
                            color = cs.tertiary
                        )
                    } else {
                        Text(
                            text = "Visto por última vez ${friend.lastSeen}",
                            fontSize = 12.sp,
                            color = cs.onSurfaceVariant
                        )
                    }
                }

                if (friend.mutualFriends > 0) {
                    Text(
                        text = "${friend.mutualFriends} amigos en común",
                        fontSize = 12.sp,
                        color = cs.onSurfaceVariant
                    )
                }
            }

            // Botones de acción
            Column {
                when (friend.relationshipType) {
                    FriendshipType.FRIEND, FriendshipType.MUTUAL -> {
                        IconButton(onClick = { onAction("message") }) {
                            Icon(
                                Icons.Default.Message,
                                contentDescription = "Mensaje",
                                tint = cs.onSurface
                            )
                        }
                    }
                    FriendshipType.FOLLOWING -> {
                        IconButton(onClick = { onAction("unfollow") }) {
                            Icon(
                                Icons.Default.PersonRemove,
                                contentDescription = "Dejar de seguir",
                                tint = cs.error
                            )
                        }
                    }
                    FriendshipType.FOLLOWER -> {
                        IconButton(onClick = { onAction("follow_back") }) {
                            Icon(
                                Icons.Default.PersonAdd,
                                contentDescription = "Seguir",
                                tint = cs.tertiary
                            )
                        }
                    }
                    FriendshipType.NONE -> {
                        IconButton(onClick = { onAction("add_friend") }) {
                            Icon(
                                Icons.Default.PersonAdd,
                                contentDescription = "Agregar amigo",
                                tint = cs.onSurface
                            )
                        }
                    }
                }

                IconButton(onClick = { onAction("view_profile") }) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Ver perfil",
                        tint = cs.onSurfaceVariant
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
    val cs = MaterialTheme.colorScheme
    val (message, icon) = when (tabIndex) {
        0 -> "Sin actividad reciente" to Icons.Default.Group
        1 -> "Aún no sigues a nadie" to Icons.Default.PersonAdd
        2 -> "Aún no tienes seguidores" to Icons.Default.People
        else -> "" to Icons.Default.Group
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
            tint = cs.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            color = cs.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Busca y agrega a tu primer amigo",
            fontSize = 14.sp,
            color = cs.onSurfaceVariant
        )
    }
}
