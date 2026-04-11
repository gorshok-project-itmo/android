package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.control.DropdownControl
import com.example.smartpot.ui.components.sensor.CompassDisplay
import com.example.smartpot.ui.components.sensor.LightDisplay
import com.example.smartpot.ui.kit.BackButton
import com.example.smartpot.ui.models.DeviceViewModel
import com.example.smartpot.util.Plant
import com.example.smartpot.util.plants

@Composable
fun SensorScreen(navController: NavController, vm: DeviceViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    var plant by remember<MutableState<Plant?>> { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(16.dp))

        BackButton(
            buttonText = "К списку устройств",
            route = Screen.DeviceList.route,
            popUpTo = Screen.Sensor.route,
            navController = navController
        )

        H2("Сенсоры")

        DropdownControl(
            "Вид растения",
            "Выберите вид растения",
            plants.associateWith { it.title }
        ) {
            plant = it
        }

        Spacer(Modifier.height(32.dp))

        Row {
            Column(modifier = Modifier.weight(3f)) {
                CompassDisplay(plant?.preferredAngle)
            }

            Column(modifier = Modifier.weight(2f)) {
                LightDisplay(plant?.preferredLight)
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}
