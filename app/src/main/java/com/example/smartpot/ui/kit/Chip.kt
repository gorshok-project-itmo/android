package com.example.smartpot.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}