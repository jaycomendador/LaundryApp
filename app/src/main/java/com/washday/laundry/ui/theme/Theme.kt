package com.washday.laundry.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = WashdayBlue,
    onPrimary = Color.White,
    primaryContainer = WashdayBlueLight,
    onPrimaryContainer = WashdayBlue,
    background = WashdayCanvas,
    onBackground = WashdayTextPrimary,
    surface = WashdayCardBg,
    onSurface = WashdayTextPrimary,
    outline = WashdayLine
)

@Composable
fun WashdayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
