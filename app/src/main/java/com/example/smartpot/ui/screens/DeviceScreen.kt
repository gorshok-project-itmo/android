package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.smartpot.ui.components.Controls
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.Schedule
import com.example.smartpot.ui.components.Tile
import com.example.smartpot.ui.components.Tiles
import com.example.smartpot.ui.components.control.NumberControl
import com.example.smartpot.ui.models.DeviceViewModel

@Composable
fun DeviceScreen(navController: NavController, vm: DeviceViewModel) {
    val scroll = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scroll)
    ) {
        H2("Полив")

//        Controls {
//            ButtonControl(
//                title = "Ручной полив",
//                subtitle = "Вы можете начать полив вне расписания",
//                icon = when (deviceState.isRunning) {
//                    true -> Icons.Default.Close
//                    false -> Icons.Default.PlayArrow
//                }
//            ) {
//                when (deviceState.isRunning) {
//                    true -> vm.stop(deviceId)
//                    false -> vm.triggerWatering(deviceId)
//                }
//            }
//
//            Text(deviceState.isRunning.toString())
//
//            TextControl(
//                title = "Режим автоматического полива"
//            ) { }
//        }

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

        Schedule(vm)
    }
}