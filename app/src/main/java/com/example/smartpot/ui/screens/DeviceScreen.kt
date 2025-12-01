package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.smartpot.ui.components.Controls
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.Tile
import com.example.smartpot.ui.components.Tiles
import com.example.smartpot.ui.components.Schedule
import com.example.smartpot.ui.components.ScheduleEntry
import com.example.smartpot.ui.components.control.ButtonControl
import com.example.smartpot.ui.components.control.NumberControl
import com.example.smartpot.ui.components.control.TextControl
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime

@Composable
fun DeviceScreen(navController: NavController) {
    val scroll = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scroll)
    ) {
        H2("Полив")

        Controls {
            ButtonControl(
                title = "Ручной полив",
                subtitle = "Вы можете начать полив вне расписания",
                icon = Icons.Default.PlayArrow
            ) {}

            TextControl(
                title = "Режим автоматического полива"
            ) { }
        }

        Tiles(
            listOf(
                Tile("последний полив", "вчера, 19:30-19:45"),
                Tile("влажность почвы", "35%"),
                Tile("вода для полива", "есть")
            )
        )

        H2("Порог влажности")

        Controls {
            NumberControl(
                title = "Значение для начала полива",
                unit = "%"
            ) { }
        }

        Schedule(
            listOf(
                ScheduleEntry(1, DayOfWeek.MONDAY, LocalTime.of(9, 0), Duration.ofSeconds(60)),
                ScheduleEntry(2, DayOfWeek.MONDAY, LocalTime.of(12, 0), Duration.ofSeconds(120)),
                ScheduleEntry(3, DayOfWeek.TUESDAY, LocalTime.of(12, 0), Duration.ofSeconds(180)),
                ScheduleEntry(4, DayOfWeek.TUESDAY, LocalTime.of(18, 0), Duration.ofSeconds(240)),
                ScheduleEntry(5, DayOfWeek.FRIDAY, LocalTime.of(9, 0), Duration.ofSeconds(300)),
                ScheduleEntry(6, DayOfWeek.FRIDAY, LocalTime.of(18, 0), Duration.ofSeconds(360))
            )
        )
    }
}