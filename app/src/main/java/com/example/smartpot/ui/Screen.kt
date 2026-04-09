package com.example.smartpot.ui

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Начало")
    object DeviceList : Screen("device_list", "Список устройств")
    object Device : Screen("device/{id}", "Устройство") {
        fun createRoute(id: Int) = "device/$id"
    }
    object Signup : Screen("signup", "Регистрация")
    object Login : Screen("login", "Вход")
    object Splash : Screen("splash", "Загрузка")
    object Sensor : Screen("sensor", "Сенсоры")
}

fun getScreen(route: String?): Screen {
    if (route == null) {
        return Screen.Home
    }

    return when {
        route == "home" -> Screen.Home
        route == "device_list" -> Screen.DeviceList
        route.startsWith("device") -> Screen.Device
        route == "signup" -> Screen.Signup
        route == "login" -> Screen.Login
        route == "splash" -> Screen.Splash
        route == "sensor" -> Screen.Sensor

        else -> Screen.Home
    }
}