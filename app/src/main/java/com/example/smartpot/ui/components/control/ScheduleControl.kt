package com.example.smartpot.ui.components.control

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.smartpot.data.api.WateringScheduleItem
import com.example.smartpot.util.customFormat
import com.example.smartpot.util.days

@Composable
fun ScheduleControl(scheduleItem: WateringScheduleItem, onValueChange: (WateringScheduleItem) -> Unit) {
    var showPopup by remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp)
        .clickable {
            showPopup = true
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = days[scheduleItem.dayOfWeek]!!,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = scheduleItem.startTime.customFormat(),
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text =  scheduleItem.endTime.customFormat(),
            modifier = Modifier.weight(0.3f)
        )
    }

    if (showPopup) {
        Dialog(
            onDismissRequest = { showPopup = false }
        ) {
            Surface(
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                var dayOfWeek by remember { mutableStateOf(scheduleItem.dayOfWeek) }
                var startTime by remember { mutableStateOf(scheduleItem.startTime) }
                var endTime by remember { mutableStateOf(scheduleItem.endTime) }

                Column(modifier = Modifier.padding(12.dp)) {
                    DayOfWeekDropdown("День недели", scheduleItem.dayOfWeek) {
                        dayOfWeek = it
                    }

                    Spacer(Modifier.height(8.dp))
                    TimeControl("Время начала", startTime) {
                        startTime = it
                    }

                    Spacer(Modifier.height(8.dp))
                    TimeControl("Время конца", endTime) {
                        endTime = it
                    }

                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { showPopup = false }) {
                            Text("Отмена")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = {
                            onValueChange(
                                scheduleItem.copy(
                                    dayOfWeek = dayOfWeek,
                                    startTime = startTime,
                                    endTime = endTime
                                )
                            )
                            showPopup = false
                        }) {
                            Text("ОК")
                        }
                    }
                }
            }
        }
    }
}