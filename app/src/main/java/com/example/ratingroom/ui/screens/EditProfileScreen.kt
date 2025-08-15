package com.example.ratingroom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratingroom.R
import com.example.ratingroom.ui.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    displayName: String,
    onDisplayNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    biography: String,
    onBiographyChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    favoriteGenre: String,
    onFavoriteGenreChange: (String) -> Unit,
    birthdate: String,
    onBirthdateChange: (String) -> Unit,
    website: String,
    onWebsiteChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = listOf("Sci-Fi", "Acción", "Drama", "Comedia", "Terror", "Romance")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // FOTO DE PERFIL
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.LightGray,
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(Modifier.fillMaxSize())
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Black, CircleShape)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = stringResource(id = R.string.change_photo),
                        tint = Color.White
                    )
                }
            }
            Text(
                stringResource(id = R.string.change_photo_hint),
                fontSize = 12.sp,
                color = Color.White
            )
        }

        Spacer(Modifier.height(24.dp))

        // INFORMACIÓN PERSONAL
        SectionCard(
            title = stringResource(id = R.string.personal_info_title)
        ) {
            TextInputField(
                value = displayName,
                onValueChange = onDisplayNameChange,
                label = stringResource(id = R.string.display_name_label),
                placeholder = stringResource(id = R.string.display_name_placeholder)
            )

            Spacer(Modifier.height(16.dp))
            
            EmailField(
                value = email,
                onValueChange = onEmailChange,
                label = stringResource(id = R.string.email_label),
                placeholder = stringResource(id = R.string.email_placeholder)
            )

            Spacer(Modifier.height(16.dp))
            
            TextInputField(
                value = biography,
                onValueChange = onBiographyChange,
                label = stringResource(id = R.string.biography_label),
                placeholder = stringResource(id = R.string.biography_placeholder)
            )

            Spacer(Modifier.height(16.dp))
            
            TextInputField(
                value = location,
                onValueChange = onLocationChange,
                label = stringResource(id = R.string.location_label),
                placeholder = stringResource(id = R.string.location_placeholder)
            )
        }

        Spacer(Modifier.height(16.dp))

        // PREFERENCIAS
        SectionCard(
            title = stringResource(id = R.string.preferences_title)
        ) {
            DropdownField(
                value = favoriteGenre,
                onValueChange = onFavoriteGenreChange,
                label = stringResource(id = R.string.favorite_genre_label),
                options = genres
            )

            Spacer(Modifier.height(16.dp))
            
            DatePickerField(
                value = birthdate,
                onValueChange = onBirthdateChange,
                label = stringResource(id = R.string.birthdate_label)
            )

            Spacer(Modifier.height(16.dp))
            
            TextInputField(
                value = website,
                onValueChange = onWebsiteChange,
                label = stringResource(id = R.string.website_label),
                placeholder = stringResource(id = R.string.website_placeholder),
                keyboardType = KeyboardType.Uri
            )
        }

        Spacer(Modifier.height(32.dp))

        CustomButton(
            text = stringResource(id = R.string.save_changes),
            onClick = { /* Guardar cambios */ },
            backgroundColor = Color.Black,
            textColor = Color.White,
            modifier = Modifier.height(56.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(
        displayName = "",
        onDisplayNameChange = {},
        email = "",
        onEmailChange = {},
        biography = "",
        onBiographyChange = {},
        location = "",
        onLocationChange = {},
        favoriteGenre = "Género Favorito",
        onFavoriteGenreChange = {},
        birthdate = "mm / dd / yyyy",
        onBirthdateChange = {},
        website = "",
        onWebsiteChange = {}
    )
}
