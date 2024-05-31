package com.sopt.compose_study.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    onPrimary = Color.White,
    primaryContainer = Purple700,
    secondary = Teal200,
    onSecondary = Color.Black,
    // Add other colors as needed
)

private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    onPrimary = Color.White,
    primaryContainer = Purple700,
    secondary = Teal200,
    onSecondary = Color.Black,
    // Add other colors as needed
)

@Composable
fun ViewModelTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,        // Update this if needed
        content = content
    )
}
