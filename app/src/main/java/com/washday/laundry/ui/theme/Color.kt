package com.washday.laundry.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Light Blue Palette
val LightBluePrimary = Color(0xFF0284C7)      // Sky Blue Primary
val LightBlueHover = Color(0xFF0369A1)        // Deep Sky Blue Hover
val LightBlueContainer = Color(0xFFE0F2FE)    // Light Ice Blue Container
val LightBlueSoftBg = Color(0xFFF0F9FF)       // Very Soft Light Blue Background

val LaundryMateCanvas = Color(0xFFF8FAFC)
val LaundryMateCardBg = Color(0xFFFFFFFF)
val LaundryMateLine = Color(0xFFBAE6FD)       // Soft Light Blue Line Border

val LaundryTextPrimary = Color(0xFF0F172A)
val LaundryTextMuted = Color(0xFF64748B)

val LaundryGreen = Color(0xFF10B981)
val LaundryGreenLight = Color(0xFFECFDF5)
val LaundryAmber = Color(0xFFF59E0B)
val LaundryAmberLight = Color(0xFFFFFBEB)

// Light Blue Gradient Brush
val LightBlueBrush = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF0284C7),
        Color(0xFF0EA5E9),
        Color(0xFF38BDF8)
    )
)

// Legacy / Compatibility Mappings pointing to Light Blue
val LaundryPinkPrimary = LightBluePrimary
val LaundryPinkDark = LightBlueHover
val LaundryPinkLight = LightBlueContainer
val LaundryPinkSoftBg = LightBlueSoftBg
val RainbowBrush = LightBlueBrush

val WashdayBlue = LightBluePrimary
val WashdayBlueHover = LightBlueHover
val WashdayBlueLight = LightBlueContainer
val WashdayCanvas = LaundryMateCanvas
val WashdayCardBg = LaundryMateCardBg
val WashdayLine = LaundryMateLine
val WashdayTextPrimary = LaundryTextPrimary
val WashdayTextMuted = LaundryTextMuted
val WashdayGreen = LaundryGreen
val WashdayGreenLight = LaundryGreenLight
val WashdayAmber = LaundryAmber
val WashdayAmberLight = LaundryAmberLight
val WashdayCoral = LightBluePrimary
