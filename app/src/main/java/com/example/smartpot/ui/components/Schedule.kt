package com.example.smartpot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartpot.ui.components.control.ScheduleControl
import com.example.smartpot.ui.models.DeviceViewModel
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun Schedule(vm: DeviceViewModel) {
    val scheduleState = vm.schedule.collectAsState()
    val schedule = scheduleState.value.schedule

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("День недели", modifier = Modifier.weight(0.4f), fontWeight = FontWeight.SemiBold)
            Text("Начало", modifier = Modifier.weight(0.3f), fontWeight = FontWeight.SemiBold)
            Text("Конец", modifier = Modifier.weight(0.3f), fontWeight = FontWeight.SemiBold)
        }

        Column {
            for (entry in schedule.values) {
                ScheduleControl(entry) {
                    vm.putWateringSchedule(it)
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
                vm.postWateringSchedule(
                    startTime = LocalTime.of(15, 0),
                    endTime = LocalTime.of(15, 15),
                    dayOfWeek = DayOfWeek.FRIDAY
                )
            }) {
                Text("Добавить")
            }
        }
    }
}