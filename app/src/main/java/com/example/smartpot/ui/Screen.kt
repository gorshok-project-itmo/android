package com.example.smartpot.ui

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Начало")
    object DeviceList : Screen("device_list", "Список устройств")
    object Device : Screen("device", "Устройство")
    object Signup : Screen("signup", "Регистрация")
    object Login : Screen("login", "Вход")
    object Splash : Screen("splash", "Загрузка")
}

fun getScreen(route: String?) =
    when (route) {
        "home" -> Screen.Home
        "device_list" -> Screen.DeviceList
        "device" -> Screen.Device
        "signup" -> Screen.Signup
        "login" -> Screen.Login
        "splash" -> Screen.Splash

        else -> Screen.Home
    }
