package com.example.smartpot.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartpot.ui.models.DeviceViewModel
import com.example.smartpot.ui.screens.DeviceListScreen
import com.example.smartpot.ui.screens.DeviceScreen
import com.example.smartpot.ui.screens.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController, vm: DeviceViewModel) {
    NavHost(navController = navController, startDestination = Screen.Device.route, modifier = Modifier.fillMaxSize()) {
        composable(Screen.Home.route) {
            HomeScreen(navController, vm)
        }

        composable(Screen.DeviceList.route) {
            DeviceListScreen(navController, vm)
        }

        composable(Screen.Device.route) {
            DeviceScreen(navController, vm)
        }
    }
}