package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.Controls
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.Schedule
import com.example.smartpot.ui.components.Tile
import com.example.smartpot.ui.components.Tiles
import com.example.smartpot.ui.components.control.IntControl
import com.example.smartpot.ui.components.control.ToggleInput
import com.example.smartpot.ui.kit.BackButton
import com.example.smartpot.ui.models.DeviceViewModel

@Composable
fun DeviceScreen(navController: NavController, deviceId: String, vm: DeviceViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val deviceState = vm.device.collectAsState()

    LaunchedEffect(vm) {
        vm.getDevice(deviceId)
        vm.getWateringSchedules(deviceId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(8.dp))

        BackButton(
            buttonText = "Ко всем устройствам",
            route = Screen.HomeTabs.main,
            popUpTo = Screen.Device.createRoute(deviceId),
            navController = navController
        )

        if (deviceState.value == null) {
            return
        }

        val device = deviceState.value!!.device

        H2(device.name)

        Tiles(
            listOf(
                Tile(
                    "Уровень воды",
                    "Заполненность резервуара",
                    "${(device.waterLevel).toInt()}%"
                ),
                Tile(
                    "Порог влажности",
                    "Полив начнётся ниже этого значения",
                    "${((device.humidityThreshold * 100).toInt())}%"
                )
            )
        )

        ToggleInput(
            title = "Статус полива",
            subtitleEnabled = "Идёт сейчас. Нажмите, чтобы выключить",
            subtitleDisabled = "Не идёт. Нажмите, чтобы включить",
            subtitleLoading = "Включение..."
        )

//        H2("Настройки")
//
//        Controls {
//            IntControl(
//                title = "Порог влажности для начала полива",
//                unit = "%",
//                value = ((device.humidityThreshold * 100).toInt()),
//                onValueChange = {
//                    vm.setHumidityThreshold(it)
//                }
//            )
//
//            TextControl(
//                title = "Название устройства",
//                value = device.name,
//                onValueChange = {
//                    vm.setDeviceName(it)
//                }
//            )
//        }

        H2("Расписание полива")

        Spacer(Modifier.height(8.dp))

        Schedule(vm)

        Spacer(Modifier.height(16.dp))
    }
}
