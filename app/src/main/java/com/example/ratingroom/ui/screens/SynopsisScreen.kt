package com.example.ratingroom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.AppTopBar
import com.example.ratingroom.ui.utils.TopBarConfig
import com.example.ratingroom.ui.utils.SectionCard

data class CastMember(val name: String, val character: String, val isMain: Boolean = false)

@Composable
fun SynopsisScreen(
    movie: Movie,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme

    // Demo de reparto (luego lo conectas a tu repo)
    val cast = listOf(
        CastMember("Actor Principal", "Protagonista", true),
        CastMember("Actriz Principal", "Protagonista", true),
        CastMember("Actor Secundario 1", "Secundario"),
        CastMember("Actriz Secundaria 2", "Secundaria")
    )

    Scaffold(
        topBar = {
            AppTopBar(
                TopBarConfig(
                    title = "Sinopsis - ${movie.title}",
                    showBackButton = true,
                    onBackClick = onBackClick
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Cabecera con info básica y poster gris (placeholder)
            Surface(
                color = cs.surface,
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(cs.surfaceVariant)
                    )
                    Column(Modifier.weight(1f)) {
                        Text(movie.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = cs.onSurface)
                        Text("Año: ${movie.year}", fontSize = 12.sp, color = cs.onSurfaceVariant)
                        Text("Director: ${movie.director}", fontSize = 12.sp, color = cs.onSurfaceVariant)
                        Text("Duración: ${movie.duration}", fontSize = 12.sp, color = cs.onSurfaceVariant)
                        Text("Género: ${movie.genre}", fontSize = 12.sp, color = cs.onSurfaceVariant)
                    }
                }
            }

            // Sinopsis extendida
            SectionCard(title = "Sinopsis") {
                Text(
                    text = buildLongSynopsis(movie.description),
                    fontSize = 14.sp,
                    color = cs.onSurface,
                    lineHeight = 20.sp
                )
            }

            // Reparto principal
            SectionCard(title = "Reparto Principal") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    cast.filter { it.isMain }.forEach { CastRow(it) }
                }
            }

            // Reparto adicional
            SectionCard(title = "Reparto") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    cast.filter { !it.isMain }.forEach { CastRow(it) }
                }
            }
        }
    }
}

@Composable
private fun CastRow(member: CastMember) {
    val cs = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(cs.surfaceVariant)
        )
        Column(Modifier.weight(1f)) {
            Text(member.name, fontWeight = FontWeight.SemiBold, color = cs.onSurface)
            Text(
                member.character,
                color = cs.onSurfaceVariant,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (member.isMain) {
            AssistChip(onClick = { }, label = { Text("Principal") })
        }
    }
}

private fun buildLongSynopsis(base: String): String {
    val extras =
        " A través de una puesta en escena cuidada, la historia explora temas de amor, " +
                "sacrificio y destino. La dirección potencia los conflictos internos mientras el " +
                "mundo alrededor presenta retos extraordinarios. La cinta ha sido aclamada por la " +
                "crítica y el público, dejando huella en la historia del cine."
    return base + "\n\n" + extras
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SynopsisScreenPreview() {
    RatingRoomTheme {
        val sample = Movie(
            id = 1,
            title = "Titanic",
            year = "1997",
            duration = "3h 14min",
            genre = "Romance",
            director = "James Cameron",
            rating = 4.5,
            reviews = 3876,
            description = "Un aristócrata de diecisiete años se enamora de un artista bondadoso pero pobre a bordo del lujoso R.M.S. Titanic."
        )
        SynopsisScreen(movie = sample, onBackClick = {})
    }
}
