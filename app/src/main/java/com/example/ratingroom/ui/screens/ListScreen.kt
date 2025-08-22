package com.example.ratingroom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.repository.MovieRepository
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.MovieCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header con estadísticas
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
            // Estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Stats(
                    number = "1",
                    label = "Por ver",
                    modifier = Modifier.weight(1f)
                )

                Stats(
                    number = "2",
                    label = "Favoritos",
                    modifier = Modifier.weight(1f)
                )

                Stats(
                    number = "1",
                    label = "Vistos",
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
                Spacer(modifier = Modifier.height(8.dp))

                // Tabs horizontales (3 tabs: Por ver, Favoritos, Vistas)
                ListTabRow(
                    selectedTab = selectedTab,
                    onTabSelected = onTabSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido según la pestaña seleccionada
                when (selectedTab) {
                    0 -> {
                        // Por ver: mostrar una película (ejemplo id=1)
                        val movie = remember { MovieRepository.getMovieById(1) }
                        movie?.let {
                            MovieList(items = listOf(it))
                        } ?: EmptyTabContent(tabIndex = 0, modifier = Modifier.fillMaxSize())
                    }
                    1 -> {
                        // Favoritos: mostrar dos películas distintas (ejemplo id=2 y id=3)
                        val favorites = remember { listOfNotNull(MovieRepository.getMovieById(2), MovieRepository.getMovieById(3)) }
                        if (favorites.isEmpty()) EmptyTabContent(tabIndex = 1, modifier = Modifier.fillMaxSize()) else MovieList(items = favorites)
                    }
                    2 -> {
                        // Vistos: mostrar una película diferente (ejemplo id=4)
                        val seen = remember { MovieRepository.getMovieById(4) }
                        seen?.let { MovieList(items = listOf(it)) } ?: EmptyTabContent(tabIndex = 2, modifier = Modifier.fillMaxSize())
                    }
                    else -> EmptyTabContent(tabIndex = 0, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun Stats(
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
fun ListTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(items) { movie ->
            // Usamos la MovieCard que ya tienes en ui.utils
            MovieCard(movie = movie, onClick = { })
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
        Icon(
            imageVector = Icons.Default.VisibilityOff,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = cs.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = message, fontSize = 18.sp, color = cs.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Agrega elementos para que aparezcan aquí", fontSize = 14.sp, color = cs.onSurfaceVariant)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewListScreen() {
    var tab by remember { mutableStateOf(0) }
    RatingRoomTheme {
        ListScreen(
            selectedTab = tab,
            onTabSelected = { tab = it },
        )
    }
}
