package com.example.smartpot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun SmartPotTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}