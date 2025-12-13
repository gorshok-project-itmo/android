package com.example.smartpot.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun H2(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(
            top = 32.dp,
            start = 16.dp,
            bottom = 4.dp,
            end = 16.dp,
        )
    ) {
        Text(text, fontSize = 14.sp)
    }
}