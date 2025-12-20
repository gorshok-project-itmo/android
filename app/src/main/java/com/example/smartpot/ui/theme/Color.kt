package com.example.smartpot.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val text = Color(0xFFE3F2FD)
val accent = Color(0xFF3ACC41)
val background = Color(0xFF1C1C1C)
val background2 = Color(0xFF252323)
val background3 = Color(0xFF383D3D)
val danger = Color(0xFFE1452A)

val lightColors = darkColorScheme(
    primary = accent,
    secondary = Color(0xFF2B8040),
    background = background,
    onPrimary = background,
    onSecondary = text,
    onBackground = text,
    onSurface = text,
    onError = text,
    surface = background2,
    tertiary = background3,
    error = danger
)