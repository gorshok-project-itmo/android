package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.models.DeviceViewModel

@Composable
fun HomeScreen(navController: NavController, vm: DeviceViewModel) {
    Column {
        H2("welcome")
    }
}