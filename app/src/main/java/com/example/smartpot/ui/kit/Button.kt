package com.example.smartpot.ui.kit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SmartPotButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonHeight: Int = 56,
    enabled: Boolean = true,
    onClickAction: () -> Unit
) {
    Button(
        onClick = onClickAction,
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.small,
        enabled = enabled
    ) {
        Text(
            buttonText,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
