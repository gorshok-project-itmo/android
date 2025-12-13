package com.example.smartpot.ui.components.control

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartpot.util.customFormat
import java.time.LocalTime

@Composable
fun TimeControl(
    title: String,
    value: LocalTime,
    is24Hour: Boolean = true,
    onTimeSelected: (value: LocalTime) -> Unit
) {
    val context = LocalContext.current
    val hour = remember { mutableIntStateOf(value.hour) }
    val minute = remember { mutableIntStateOf(value.minute) }

    val dialog = remember {
        TimePickerDialog(
            context,
            { _: TimePicker, h: Int, m: Int ->
                hour.intValue = h; minute.intValue = m
                onTimeSelected(LocalTime.of(h, m))
            },
            hour.intValue,
            minute.intValue,
            is24Hour
        )
    }

    LaunchedEffect(Unit) { /* no-op to keep remember stable */ }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { dialog.show() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, fontSize = 18.sp)
            Text(LocalTime.of(hour.intValue, minute.intValue).customFormat())
        }
    }
}
