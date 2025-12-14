package com.example.smartpot.ui.components.control

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.smartpot.util.customFormat
import java.time.LocalTime

@Composable
fun TimeControl(
    title: String,
    value: LocalTime,
    is24Hour: Boolean = true,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val hour = remember { mutableIntStateOf(value.hour) }
    val minute = remember { mutableIntStateOf(value.minute) }

    val dialog = remember {
        TimePickerDialog(
            context,
            { _: TimePicker, h: Int, m: Int ->
                hour.intValue = h
                minute.intValue = m
                onTimeSelected(LocalTime.of(h, m))
            },
            hour.intValue,
            minute.intValue,
            is24Hour
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = { dialog.show() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(LocalTime.of(hour.intValue, minute.intValue).customFormat(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}
