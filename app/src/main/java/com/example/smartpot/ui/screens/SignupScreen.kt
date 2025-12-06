package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.models.SignupViewModel

@Composable
fun SignupScreen(navController: NavController, vm: SignupViewModel = hiltViewModel()) {
    val email by remember { vm.email }
    val password by remember { vm.password }
    val confirmPassword by remember { vm.confirmPassword }
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
        H2("Регистрация")

        OutlinedTextField(
            value = email,
            onValueChange = vm::onEmailChange,
            label = { Text("Email") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = vm::onPasswordChange,
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = vm::onConfirmPasswordChange,
            label = { Text("Подтверждение пароля") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { vm.signup() },
            enabled = !loading
        ) {
            if (loading) Text("Регистрация...") else Text("Зарегистрироваться")
        }

        Spacer(Modifier.height(8.dp))

        error?.let {
            Text(it, color = Color.Red)
        }

        Row {
            Text("Уже есть аккаунт?")

            Button(onClick = {
                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                }
            }) {
                Text("Войти")
            }
        }
    }
}