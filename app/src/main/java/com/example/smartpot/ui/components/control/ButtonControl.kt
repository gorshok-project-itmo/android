package com.example.smartpot.ui.components.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonControl(title: String, subtitle: String, icon: ImageVector, action: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, fontSize = 18.sp)
            Text(subtitle, fontSize = 14.sp)
        }

        IconButton(
            onClick = action,
            Modifier.size(48.dp).padding(0.dp).background(Color.White)
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                tint = Color.Black
            )
        }
    }
}