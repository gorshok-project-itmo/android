package com.example.smartpot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartpot.ui.components.control.ScheduleControl
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.models.DeviceViewModel
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun Schedule(vm: DeviceViewModel) {
    val scheduleState = vm.schedule.collectAsState()
    val schedule = scheduleState.value.schedule

    Column(modifier = Modifier.fillMaxWidth()) {
        if (schedule.isEmpty()) {
            Column {
                Text("Нет расписания", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("День недели", modifier = Modifier.weight(0.4f), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                Text("Начало", modifier = Modifier.weight(0.3f), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                Text("Конец", modifier = Modifier.weight(0.3f), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
            }

            Column {
                schedule.values.forEach { entry ->
                    ScheduleControl(entry) {
                        vm.putWateringSchedule(it)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmartPotButton(
                buttonText = if (schedule.isEmpty()) "Создать расписание" else "Добавить строку",
                onClickAction = {
                    vm.postWateringSchedule(
                        startTime = LocalTime.of(15, 0),
                        endTime = LocalTime.of(15, 15),
                        dayOfWeek = DayOfWeek.FRIDAY
                    )
                }
            )
        }
    }
}