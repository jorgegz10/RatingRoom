package com.example.ratingroom.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light scheme (Material Design 3)
private val LightColors = lightColorScheme(
    primary = Color(0xFF415EC2),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD9E1FF),
    onPrimaryContainer = Color(0xFF00174A),
    secondary = Color(0xFF19243A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFDDE2F7),
    onSecondaryContainer = Color(0xFF121A2C),
    tertiary = Color(0xFFC5A052),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFFFE7B6),
    onTertiaryContainer = Color(0xFF3B2E00),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
)

// Dark scheme (Material Design 3)
private val DarkColors = darkColorScheme(
    primary = Color(0xFFAFC6FF),
    onPrimary = Color(0xFF001A4D),
    primaryContainer = Color(0xFF2747A4),
    onPrimaryContainer = Color(0xFFD9E1FF),
    secondary = Color(0xFFBFC6E4),
    onSecondary = Color(0xFF131A2E),
    secondaryContainer = Color(0xFF2F3650),
    onSecondaryContainer = Color(0xFFDDE2F7),
    tertiary = Color(0xFFE6C478),
    onTertiary = Color(0xFF3B2E00),
    tertiaryContainer = Color(0xFF5B4617),
    onTertiaryContainer = Color(0xFFFFE7B6),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF121212),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
)

@Composable
fun RatingRoomTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(), // auto: follows system setting
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
