package com.example.smartpot.ui

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Главная")
    object Device : Screen("device/{id}", "Устройство") {
        fun createRoute(id: String) = "device/$id"
    }
    object Signup : Screen("signup", "Регистрация")
    object Login : Screen("login", "Вход")
    object Splash : Screen("splash", "Загрузка")

    object HomeTabs {
        const val route = "home"
        const val main = "home/main"
        const val sensor = "home/sensor"
        const val profile = "home/profile"
    }
}
