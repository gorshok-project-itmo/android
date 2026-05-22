package com.example.smartpot.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val text = Color(0xFFEBF4FC)
val text1 = Color(0xFF8C96A7)
val text2 = Color(0xFF4B5563)
val accent = Color(0xFF22C55E)
val accent2 = Color(0xFF0D2314)
val background = Color(0xFF0A0A0A)
val background2 = Color(0xFF141414)
val background3 = Color(0xFF383D3D)
val danger = Color(0xFFE1452A)

val lightColors = darkColorScheme(
    primary = accent,
    secondary = accent2,
    onTertiary = text1,
    background = background,
    onPrimary = text,
    onSecondary = text,
    onBackground = text,
    onSurface = text,
    onError = text,
    surface = background2,
    tertiary = background3,
    error = danger,
)