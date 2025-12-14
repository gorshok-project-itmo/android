package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.data.api.Device
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.models.DeviceListViewModel

@Composable
fun DeviceListScreen(navController: NavController, vm: DeviceListViewModel = hiltViewModel()) {
    val devicesState = vm.devices.collectAsState()
    val devices = devicesState.value.devices

    LaunchedEffect(vm) {
        vm.getDevices()

        vm.loggedOutEvent.collect {
            navController.navigate("login")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.height(16.dp))

        H2("Список устройств")

        LazyColumn {
            items(devices.values.toList()) { entry ->
                DeviceListItem(entry, navController)
            }
        }

        H2("Параметры")

        SmartPotButton(
            buttonText = "Выйти из аккаунта",
            onClickAction = { vm.logout() }
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun DeviceListItem(device: Device, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate(Screen.Device.createRoute(device.id)) {
                popUpTo(Screen.DeviceList.route) { inclusive = true }
            }
        }
    ) {
        Row (
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Home,
                contentDescription = device.name,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text("${device.name} (id ${device.id})", style = MaterialTheme.typography.titleLarge)
        }
    }
}