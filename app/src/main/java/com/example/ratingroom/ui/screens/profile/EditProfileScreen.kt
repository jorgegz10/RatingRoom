package com.example.ratingroom.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ratingroom.R
import com.example.ratingroom.ui.theme.RatingRoomTheme
import com.example.ratingroom.ui.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onSave: () -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Estado para controlar la visibilidad del diálogo de confirmación
    var showConfirmDialog by remember { mutableStateOf(false) }
    
    // Launcher para seleccionar imagen de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { 
            println("URI seleccionada en EditProfileScreen: $it")
            println("URI scheme: ${it.scheme}, path: ${it.path}")
            viewModel.onProfileImageSelected(it)
            // Guardar automáticamente cuando se selecciona una imagen
            viewModel.saveProfile()
        }
    }

    // Diálogo de confirmación para salir sin guardar
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("¿Salir sin guardar?") },
            text = { Text("Los cambios no guardados se perderán.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        onBackClick()
                    }
                ) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
    
    // Función para manejar el botón de retroceso
    val handleBackClick = {
        // Si hay una imagen seleccionada, guardar automáticamente
        if (uiState.profileImageUri != null) {
            viewModel.saveProfile()
            onSave()
        } else {
            // Si hay otros cambios, mostrar diálogo de confirmación
            showConfirmDialog = true
        }
    }
    
    EditProfileScreenContent(
        uiState = uiState,
        onDisplayNameChange = viewModel::onDisplayNameChange,
        onEmailChange = viewModel::onEmailChange,
        onBiographyChange = viewModel::onBiographyChange,
        onLocationChange = viewModel::onLocationChange,
        onFavoriteGenreChange = viewModel::onFavoriteGenreChange,
        onBirthdateChange = viewModel::onBirthdateChange,
        onWebsiteChange = viewModel::onWebsiteChange,
        onSelectImage = { galleryLauncher.launch("image/*") },
        onSave = {
            viewModel.saveProfile()
            onSave()
        },
        onBackClick = handleBackClick,
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
    onSelectImage: () -> Unit,
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
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(cs.surfaceVariant)
                            .border(2.dp, cs.primary, CircleShape)
                            .clickable(onClick = onSelectImage)
                    ) {
                        if (uiState.profileImageUri != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uiState.profileImageUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(id = R.string.profile_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(Modifier.fillMaxSize())
                        }
                    }
                    IconButton(
                        onClick = onSelectImage,
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
                website = "https://miweb.com",
                profileImageUri = null
            ),
            onDisplayNameChange = {},
            onEmailChange = {},
            onBiographyChange = {},
            onLocationChange = {},
            onFavoriteGenreChange = {},
            onBirthdateChange = {},
            onWebsiteChange = {},
            onSelectImage = {},
            onSave = {},
            onBackClick = {}
        )
    }
}
