package com.example.smartpot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.example.smartpot.ui.components.control.TextInput
import com.example.smartpot.ui.kit.SmartPotButton
import com.example.smartpot.ui.kit.SmartPotButtonSecondary
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        H2("Регистрация")

        Spacer(Modifier.height(16.dp))

        Form {
            TextInput(
                value = email,
                onValueChange = vm::onEmailChange,
                placeholder = "Email"
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextInput(
                value = password,
                onValueChange = vm::onPasswordChange,
                placeholder = "Пароль",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextInput(
                value = confirmPassword,
                onValueChange = vm::onConfirmPasswordChange,
                placeholder = "Подтверждение пароля",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            SmartPotButton(
                buttonText = if (loading) "Регистрация..." else "Зарегистрироваться",
                enabled = !loading,
                onClickAction = { vm.signup() }
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
                SmartPotButtonSecondary(
                    buttonText = "Уже есть аккаунт? Войти",
                    onClickAction = {
                        navController.navigate("login") {
                            popUpTo("signup") { inclusive = true }
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}
