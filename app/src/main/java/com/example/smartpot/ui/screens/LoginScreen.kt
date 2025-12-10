package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.components.Form
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.models.LoginViewModel
import com.example.smartpot.ui.models.SignupViewModel

@Composable
fun LoginScreen(navController: NavController, vm: LoginViewModel = hiltViewModel()) {
    val email by remember { vm.email }
    val password by remember { vm.password }
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    LaunchedEffect(vm) {
        vm.signedInEvent.collect { token ->
            navController.navigate("home") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column {
        H2("Вход")

        Form {
            OutlinedTextField(
                value = email,
                onValueChange = vm::onEmailChange,
                label = { Text("Email") }
            )

            OutlinedTextField(
                value = password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = { vm.login() },
                enabled = !loading
            ) {
                if (loading) Text("Вход...") else Text("Войти")
            }

            error?.let {
                Text(it, color = Color.Red)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Ещё нет аккаунта?")

                Spacer(Modifier.width(8.dp))

                Button(onClick = {
                    navController.navigate("signup") {
                        popUpTo("login") { inclusive = true }
                    }
                }) {
                    Text("Зарегистрироваться")
                }
            }
        }
    }
}