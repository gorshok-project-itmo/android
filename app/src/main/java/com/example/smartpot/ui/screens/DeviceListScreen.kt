package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.models.DeviceListViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun DeviceListScreen(navController: NavController, vm: DeviceListViewModel = hiltViewModel()) {
    val devicesState = vm.devices.collectAsState()
    val devices = devicesState.value.devices

    LaunchedEffect(vm) {
        vm.getDevices()
    }

    Column {
        H2("Список устройств")

        for (entry in devices.values) {
            Row {
                Button(
                    onClick = {
                        navController.navigate(Screen.Device.createRoute(entry.id)) {
                            popUpTo(Screen.DeviceList.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("${entry.name} (id ${entry.id})")
                }
            }
        }
    }
}