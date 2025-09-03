package com.example.ratingroom.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onSave: () -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    EditProfileScreenContent(
        uiState = uiState,
        onDisplayNameChange = viewModel::onDisplayNameChange,
        onEmailChange = viewModel::onEmailChange,
        onBiographyChange = viewModel::onBiographyChange,
        onLocationChange = viewModel::onLocationChange,
        onFavoriteGenreChange = viewModel::onFavoriteGenreChange,
        onBirthdateChange = viewModel::onBirthdateChange,
        onWebsiteChange = viewModel::onWebsiteChange,
        onSave = {
            viewModel.saveProfile()
            onSave()
        },
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreenContent(
    uiState: EditProfileUIState,
    onDisplayNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onBiographyChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onFavoriteGenreChange: (String) -> Unit,
    onBirthdateChange: (String) -> Unit,
    onWebsiteChange: (String) -> Unit,
    onSave: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = listOf("Sci-Fi", "Acción", "Drama", "Comedia", "Terror", "Romance")
    val cs = MaterialTheme.colorScheme

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header SOLO con flecha atrás (SIN LOGO)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_desc_back),
                        tint = cs.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

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
                        color = cs.surfaceVariant,
                        modifier = Modifier.size(100.dp)
                    ) { Box(Modifier.fillMaxSize()) }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(32.dp)
                            .background(cs.primary, CircleShape)
                    ) {
                        Icon(
                            Icons.Filled.CameraAlt,
                            contentDescription = stringResource(id = R.string.change_photo),
                            tint = cs.onPrimary
                        )
                    }
                }
                Text(
                    stringResource(id = R.string.change_photo_hint),
                    fontSize = 12.sp,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(Modifier.height(24.dp))

            // INFORMACIÓN PERSONAL
            SectionCard(title = stringResource(id = R.string.personal_info_title)) {
                TextInputField(
                    value = uiState.displayName,
                    onValueChange = onDisplayNameChange,
                    label = stringResource(id = R.string.display_name_label),
                    placeholder = stringResource(id = R.string.display_name_placeholder)
                )

                Spacer(Modifier.height(16.dp))

                EmailField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = stringResource(id = R.string.email_label),
                    placeholder = stringResource(id = R.string.email_placeholder)
                )

                Spacer(Modifier.height(16.dp))

                TextInputField(
                    value = uiState.biography,
                    onValueChange = onBiographyChange,
                    label = stringResource(id = R.string.biography_label),
                    placeholder = stringResource(id = R.string.biography_placeholder)
                )

                Spacer(Modifier.height(16.dp))

                TextInputField(
                    value = uiState.location,
                    onValueChange = onLocationChange,
                    label = stringResource(id = R.string.location_label),
                    placeholder = stringResource(id = R.string.location_placeholder)
                )
            }

            Spacer(Modifier.height(16.dp))

            // PREFERENCIAS
            SectionCard(title = stringResource(id = R.string.preferences_title)) {
                DropdownField(
                    value = uiState.favoriteGenre,
                    onValueChange = onFavoriteGenreChange,
                    label = stringResource(id = R.string.favorite_genre_label),
                    options = genres
                )

                Spacer(Modifier.height(16.dp))

                DatePickerField(
                    value = uiState.birthdate,
                    onValueChange = onBirthdateChange,
                    label = stringResource(id = R.string.birthdate_label)
                )

                Spacer(Modifier.height(16.dp))

                TextInputField(
                    value = uiState.website,
                    onValueChange = onWebsiteChange,
                    label = stringResource(id = R.string.website_label),
                    placeholder = stringResource(id = R.string.website_placeholder),
                    keyboardType = KeyboardType.Uri
                )
            }

            Spacer(Modifier.height(32.dp))

            CustomButton(
                text = stringResource(id = R.string.save_changes),
                onClick = onSave,
                backgroundColor = cs.primary,
                textColor = cs.onPrimary,
                modifier = Modifier.height(56.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    RatingRoomTheme {
        EditProfileScreenContent(
            uiState = EditProfileUIState(
                displayName = "Pedro",
                email = "pedro@correo.com",
                biography = "Amante del cine y las buenas historias.",
                location = "Madrid, España",
                favoriteGenre = "Sci-Fi",
                birthdate = "15/03/1990",
                website = "https://miweb.com"
            ),
            onDisplayNameChange = {},
            onEmailChange = {},
            onBiographyChange = {},
            onLocationChange = {},
            onFavoriteGenreChange = {},
            onBirthdateChange = {},
            onWebsiteChange = {},
            onSave = {},
            onBackClick = {}
        )
    }
}
