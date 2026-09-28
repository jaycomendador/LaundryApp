package com.washday.laundry.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LaundryPinkPrimary,
    onPrimary = Color.White,
    primaryContainer = LaundryPinkLight,
    onPrimaryContainer = LaundryPinkPrimary,
    background = LaundryMateCanvas,
    onBackground = LaundryTextPrimary,
    surface = LaundryMateCardBg,
    onSurface = LaundryTextPrimary,
    outline = LaundryMateLine
)

@Composable
fun LaundryMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

// Alias for compatibility
@Composable
fun WashdayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) = LaundryMateTheme(darkTheme = darkTheme, content = content)
