package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.Controls
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.Schedule
import com.example.smartpot.ui.components.Tile
import com.example.smartpot.ui.components.Tiles
import com.example.smartpot.ui.components.control.IntControl
import com.example.smartpot.ui.components.control.TextControl
import com.example.smartpot.ui.models.DeviceViewModel

@Composable
fun DeviceScreen(navController: NavController, deviceId: Int, vm: DeviceViewModel = hiltViewModel()) {
    val scroll = rememberScrollState()
    val deviceState = vm.device.collectAsState()
    val device = deviceState.value.device

    LaunchedEffect(vm) {
        vm.getDevice(deviceId)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scroll)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.DeviceList.route) {
                    popUpTo(Screen.Device.createRoute(deviceId)) { inclusive = true }
                }
            }
        ) {
            Text("Ко всем устройствам")
        }

        if (device == null) {
            H2("Устройство не найдено")
            return
        }

        H2(deviceState.value.device?.name ?: "")

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
                Tile("вода для полива", "${(device.waterLevel * 100).toInt()}%")
            )
        )

        H2("Настройки")

        Controls {
            IntControl(
                title = "Порог влажности для начала полива",
                unit = "%",
                value = ((device.humidityThreshold * 100).toInt()),
                onValueChange = { vm.setHumidityThreshold(it) }
            )

            TextControl(
                title = "Название устройства",
                value = device.name,
                onValueChange = { vm.setDeviceName(it) }
            )
        }

        Schedule(vm)
    }
}