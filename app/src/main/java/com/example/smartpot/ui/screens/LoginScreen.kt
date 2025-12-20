package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.components.Form
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.models.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, vm: LoginViewModel = hiltViewModel()) {
    val email by remember { vm.email }
    val password by remember { vm.password }
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    LaunchedEffect(vm) {
        vm.signedInEvent.collect { token ->
            navController.navigate("device_list") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(16.dp))

        H2("Вход")

        Form {
            OutlinedTextField(
                value = email,
                onValueChange = vm::onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            SmartPotButton(
                buttonText = if (loading) "Вход..." else "Войти",
                enabled = !loading,
                onClickAction = { vm.login() }
            )

            error?.let {
                Text(
                    it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Ещё нет аккаунта?")

                Spacer(Modifier.width(16.dp))

                SmartPotButton(
                    buttonText = "Зарегистрироваться",
                    onClickAction = {
                        navController.navigate("signup") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}
