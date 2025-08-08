package com.example.ratingroom.ui.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var birthdate by remember { mutableStateOf("mm / dd / yyyy") }
    val datePicker = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                birthdate = String.format("%02d / %02d / %04d", month + 1, day, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF0D0B1F), Color(0xFF1C2D64))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Editar Perfil")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Guardar cambios */ }) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(gradient)
                .padding(16.dp)
        ) {
            // FOTO
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
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
                        Icon(Icons.Default.CameraAlt, contentDescription = "Cambiar foto", tint = Color.White)
                    }
                }
                Text("Toca el ícono de cámara para cambiar tu foto", fontSize = 12.sp, color = Color.White)
            }

            Spacer(Modifier.height(24.dp))

            // INFORMACIÓN PERSONAL
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Información Personal", fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(8.dp))
                    Text("Nombre para mostrar")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Tu nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))
                    Text("Correo electrónico")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("tu@correo.com") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))
                    Text("Biografía")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Cuéntanos sobre ti y tus gustos cinematográficos...") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))
                    Text("Ubicación")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Ciudad, País") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // PREFERENCIAS
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Preferencias", fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(8.dp))
                    Text("Género favorito")
                    var selectedGenre by remember { mutableStateOf("Genero Favorito") }
                    var expanded by remember { mutableStateOf(false) }
                    val genres = listOf("Sci-Fi", "Acción", "Drama", "Comedia")

                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                        OutlinedTextField(
                            value = selectedGenre,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            genres.forEach { genre ->
                                DropdownMenuItem(
                                    text = { Text(genre) },
                                    onClick = {
                                        selectedGenre = genre
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    Text("Fecha de nacimiento")
                    OutlinedTextField(
                        value = birthdate,
                        onValueChange = {},
                        trailingIcon = {
                            IconButton(onClick = { datePicker.show() }) {
                                Icon(Icons.Default.CalendarToday, contentDescription = "Fecha")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePicker.show() },
                        readOnly = true
                    )

                    Spacer(Modifier.height(8.dp))
                    Text("Sitio web")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("https://tu-sitio.com") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Guardar Cambios", color = Color.White)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen()
}
