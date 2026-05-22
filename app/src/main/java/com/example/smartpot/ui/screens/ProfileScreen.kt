package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.models.ProfileViewModel


@Composable
fun ProfileScreen(navController: NavController, vm: ProfileViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    LaunchedEffect(vm) {
        vm.loggedOutEvent.collect {
            navController.navigate("login")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        H2("Профиль")

        Spacer(Modifier.height(8.dp))

        Text(vm.email.collectAsStateWithLifecycle().value ?: "not logged in")

        Spacer(Modifier.height(16.dp))

        SmartPotButton(
            buttonText = "Выйти из аккаунта",
            backgroundColor = MaterialTheme.colorScheme.error,
            onClickAction = { vm.logout() }
        )

        Spacer(Modifier.height(16.dp))
    }


}