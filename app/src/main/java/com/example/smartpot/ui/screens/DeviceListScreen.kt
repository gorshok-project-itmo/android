package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.models.DeviceListViewModel

@Composable
fun DeviceListScreen(navController: NavController, vm: DeviceListViewModel = hiltViewModel()) {
    Column {
        H2("device list h2")

        Button(onClick = {
            navController.navigate(Screen.Home.route)
        }) {
            Text("home")
        }
    }
}