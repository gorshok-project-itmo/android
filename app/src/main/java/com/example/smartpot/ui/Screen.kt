package com.example.smartpot.ui

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object DeviceList : Screen("device_list", "Device list")
    object Device : Screen("device", "Device")
}

fun getScreen(route: String?) =
    when (route) {
        "home" -> Screen.Home
        "device_list" -> Screen.DeviceList
        "device" -> Screen.Device

        else -> Screen.Home
    }
