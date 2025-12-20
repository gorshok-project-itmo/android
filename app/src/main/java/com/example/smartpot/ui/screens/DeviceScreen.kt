package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
import com.example.smartpot.ui.components.control.TextControl
import com.example.smartpot.ui.kit.BackButton
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.models.DeviceViewModel

@Composable
fun DeviceScreen(navController: NavController, deviceId: Int, vm: DeviceViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val deviceState = vm.device.collectAsState()
    val device = deviceState.value.device

    LaunchedEffect(vm) {
        vm.getDevice(deviceId)
        vm.getWateringSchedules(deviceId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(16.dp))

        BackButton(
            buttonText = "Ко всем устройствам",
            route = Screen.DeviceList.route,
            popUpTo = Screen.Device.createRoute(deviceId),
            navController = navController
        )

        if (device == null) {
            H2("Устройство не найдено")
            return
        }

        H2(deviceState.value.device?.name ?: "")

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
                onValueChange = {
//                    vm.setHumidityThreshold(it)
                }
            )

            TextControl(
                title = "Название устройства",
                value = device.name,
                onValueChange = {
//                    vm.setDeviceName(it)
                }
            )
        }

        H2("Расписание")

        Schedule(vm)

        Spacer(Modifier.height(16.dp))
    }
}
