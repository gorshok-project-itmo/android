package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.models.LaunchViewModel

@Composable
fun HomeScreen(navController: NavController, vm: LaunchViewModel) {
    Column {
        H2("Начало")

        Button(onClick = {
            vm.signOut()
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("Выйти")
        }
    }
}