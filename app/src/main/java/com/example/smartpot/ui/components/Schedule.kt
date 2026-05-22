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
import com.example.smartpot.ui.kit.SmartPotButtonSecondary
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
                Text("Нет расписания", style = MaterialTheme.typography.titleSmall)
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                schedule.values.forEach { entry ->
                    ScheduleControl(
                        scheduleItem = entry,
                        onValueChange = {
                            vm.putWateringSchedule(it)
                        },
                        onDelete = {
                            vm.deleteSchedule(it.id)
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmartPotButtonSecondary(
                buttonText = if (schedule.isEmpty()) "Создать расписание" else "Добавить",
                onClickAction = {
                    vm.postWateringSchedule(
                        startTime = LocalTime.of(15, 0),
                        endTime = LocalTime.of(15, 15),
                        dayOfWeek = DayOfWeek.FRIDAY
                    )
                },
                textColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                borderColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}