package com.example.smartpot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.data.api.Device
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.Tile
import com.example.smartpot.ui.components.Tiles
import com.example.smartpot.ui.components.control.TextDialog
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.kit.SmartPotButtonSecondary
import com.example.smartpot.ui.models.DeviceListViewModel

@Composable
fun DeviceListScreen(navController: NavController, vm: DeviceListViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    val devicesState = vm.devices.collectAsState()
    val devices = devicesState.value.devices

    LaunchedEffect(vm) {
        vm.getDevices()

        vm.loggedOutEvent.collect {
            navController.navigate("login")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(16.dp))

        H2("Устройства")

        Spacer(Modifier.height(4.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (devices.isEmpty()) {
                Text("Нет подключённых устройств")
            } else {
                Text("${devices.size} устройств подключено")
                Spacer(Modifier.height(4.dp))
                devices.values.toList().forEach { entry ->
                    DeviceListItem(entry, navController)
                }
            }

            var showDialog by remember { mutableStateOf(false) }

            Spacer(Modifier.height(8.dp))

            SmartPotButtonSecondary(
                buttonText = "Добавить",
                onClickAction = {
                    showDialog = true
                },
                textColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                borderColor = MaterialTheme.colorScheme.primary,
            )

            if (showDialog) {
                TextDialog(
                    title = "Добавить устройство",
                    value = "MyDevice",
                    onShowChange = { showDialog = it },
                    onValueChange = {
                        vm.addDevice(it)
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        H2("Сенсоры")

        Spacer(Modifier.height(4.dp))

        Text("Сенсор света и компас помогут выбрать оптимальное положение для растения в помещении")

        Spacer(Modifier.height(16.dp))

        SmartPotButtonSecondary(
            buttonText = "Открыть сенсоры",
            onClickAction = {
                navController.navigate("sensor") {
                    popUpTo("device_list") { inclusive = true }
                }
            },
            textColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.secondary,
            borderColor = MaterialTheme.colorScheme.primary,
        )

        Spacer(Modifier.height(16.dp))

        H2("Шаблоны")

        Text("Готовые режимы полива")

        Tiles(listOf(
            Tile("\uD83C\uDF35 Кактус", "Раз в неделю", "Лёгкий"),
            Tile("\uD83C\uDF3F Фикус", "2 раза в день", "Средний"),
            Tile("\uD83C\uDF38 Орхидея", "Раз в 3 дня", "Сложный"),
        ))

        Spacer(Modifier.height(16.dp))

        H2("Параметры")

        Spacer(Modifier.height(8.dp))

        SmartPotButton(
            buttonText = "Выйти из аккаунта",
            backgroundColor = MaterialTheme.colorScheme.error,
            onClickAction = { vm.logout() }
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun DeviceListItem(device: Device, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.tertiary
        ),
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
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(device.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}