package com.example.ratingroom.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val c = MaterialTheme.colorScheme
    Box(modifier = modifier) {
        Button(
            onClick = { onExpandedChange(true) },
            colors = ButtonDefaults.buttonColors(
                containerColor = c.secondaryContainer,
                contentColor = c.onSecondaryContainer
            )
        ) {
            Text(stringResource(id = R.string.main_menu_filters), color = c.onSecondaryContainer)
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
