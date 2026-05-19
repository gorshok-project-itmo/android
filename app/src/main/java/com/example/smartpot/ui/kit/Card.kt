package com.example.smartpot.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.card(): Modifier = this
    .background(
    MaterialTheme.colorScheme.surface,
    MaterialTheme.shapes.large,
    )
    .border(
        1.dp,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.shapes.large
    )