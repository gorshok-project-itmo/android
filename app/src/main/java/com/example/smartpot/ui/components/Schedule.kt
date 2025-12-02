package com.example.smartpot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartpot.data.api.WateringScheduleRequest
import com.example.smartpot.ui.models.DeviceViewModel
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Schedule(vm: DeviceViewModel) {
    val deviceId = 1
    val scheduleState = vm.schedule.collectAsState()
    val schedule = scheduleState.value.schedule.filter { it.value.deviceId == deviceId }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("День", modifier = Modifier.weight(0.3f))
            Text("Начало", modifier = Modifier.weight(0.4f))
            Text("Конец", modifier = Modifier.weight(0.4f))
            Box(modifier = Modifier.width(48.dp))
        }

        Column {
            for (entry in schedule.values) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = entry.dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()),
                        modifier = Modifier.weight(0.25f)
                    )
                    Text(
                        text = entry.startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                        modifier = Modifier.weight(0.35f)
                    )
                    Text(
                        text =  entry.endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                        modifier = Modifier.weight(0.4f)
                    )
                    IconButton(
                        onClick = {},
                        Modifier.size(48.dp).padding(0.dp).background(Color.White)
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Изменить",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(onClick = {
                vm.postWateringSchedule(WateringScheduleRequest(
                    deviceId = deviceId,
                    startTime = LocalTime.of(12, 0),
                    dayOfWeek = DayOfWeek.FRIDAY,
                    endTime = LocalTime.of(12, 30),
                    active = true
                ))
            }) {
                Text("Добавить")
            }
        }
    }
}