package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.ratingroom.R

@Composable
fun FilterDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedGenre: String,
    genres: List<String>,
    onGenreSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Button(
            onClick = { onExpandedChange(true) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5A052))
        ) {
            Text(stringResource(id = R.string.main_menu_filters), color = Color.Black)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            genres.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre) },
                    onClick = {
                        onGenreSelected(genre)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}