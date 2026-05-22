package com.example.smartpot.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.card(
    clickable: Boolean = false,
    accent: Boolean = false,
    loading: Boolean = false,
): Modifier = this
    .background(
    if (accent) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
    MaterialTheme.shapes.large,
    )
    .border(
        1.dp,
        if (clickable) {
            if (loading) MaterialTheme.colorScheme.tertiary else
            if (accent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary
        } else MaterialTheme.colorScheme.tertiary,
        MaterialTheme.shapes.large
    )