package com.example.ratingroom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VisibilityOff
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
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.repository.MovieRepository
import com.example.ratingroom.ui.theme.RatingRoomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Header con estadísticas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E))
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Stats(number = "1", label = "Por ver", modifier = Modifier.weight(1f))
                Stats(number = "2", label = "Favoritos", modifier = Modifier.weight(1f))
                Stats(number = "1", label = "Vistos", modifier = Modifier.weight(1f))
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                // Tabs
                ListTabRow(selectedTab = selectedTab, onTabSelected = onTabSelected)

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido por pestaña (aquí usamos IDs fijos tal como pediste)
                when (selectedTab) {
                    0 -> {
                        // Por ver: 1 película (id = 1)
                        val movie = MovieRepository.getMovieById(1)
                        if (movie != null) MovieList(items = listOf(movie)) else EmptyTabContent(0)
                    }
                    1 -> {
                        // Favoritos: 2 películas (id = 2 y 3)
                        val favs = listOfNotNull(
                            MovieRepository.getMovieById(2),
                            MovieRepository.getMovieById(3)
                        )
                        if (favs.isNotEmpty()) MovieList(items = favs) else EmptyTabContent(1)
                    }
                    2 -> {
                        // Vistos: 1 película diferente (id = 4)
                        val seen = MovieRepository.getMovieById(4)
                        if (seen != null) MovieList(items = listOf(seen)) else EmptyTabContent(2)
                    }
                    else -> EmptyTabContent(0)
                }
            }
        }
    }
}

@Composable
fun Stats(number: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = number, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = label, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
    }
}

@Composable
fun ListTabRow(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Por ver", "Favoritos", "Vistas")
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
fun MovieList(items: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier) {
        items(items) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* navegar o show detalle */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cs.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 110.dp)
                    .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
            ) {
                // placeholder para imagen
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = cs.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "${movie.year} • ${movie.duration}", fontSize = 12.sp, color = cs.onSurfaceVariant)

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFFFFD700))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "${movie.rating}", fontWeight = FontWeight.Bold, color = cs.onSurface)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${movie.reviews} reseñas", fontSize = 12.sp, color = cs.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun EmptyTabContent(tabIndex: Int, modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    val message = when (tabIndex) {
        0 -> "Sin elementos por ver"
        1 -> "Aún no tienes favoritos"
        2 -> "Aún no has visto nada"
        else -> "Sin contenido"
    }

    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null, modifier = Modifier.size(64.dp), tint = cs.onSurfaceVariant)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = message, fontSize = 18.sp, color = cs.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Agrega elementos para que aparezcan aquí", fontSize = 14.sp, color = cs.onSurfaceVariant)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewListScreen() {
    var tab by remember { mutableStateOf(0) } // preview con estado local
    RatingRoomTheme {
        ListScreen(selectedTab = tab, onTabSelected = { tab = it })
    }
}
