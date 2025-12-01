package com.example.smartpot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.smartpot.data.mock.MockDeviceApi
import com.example.smartpot.data.repository.DeviceRepository
import com.example.smartpot.ui.AppNavHost
import com.example.smartpot.ui.models.DeviceViewModel
import com.example.smartpot.ui.theme.SmartPotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val api = MockDeviceApi()
        val repo = DeviceRepository(api)
        val vm = DeviceViewModel(repo)

        setContent {
            App(vm)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(vm: DeviceViewModel) {
    val navController = rememberNavController()

    SmartPotTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text("SmartPot")
                })
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ) {
                AppNavHost(navController, vm)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
//    App()
}