package com.example.smartpot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.models.LaunchState
import com.example.smartpot.ui.models.LaunchViewModel
import com.example.smartpot.ui.screens.DeviceListScreen
import com.example.smartpot.ui.screens.DeviceScreen
import com.example.smartpot.ui.screens.HomeScreen
import com.example.smartpot.ui.screens.LoginScreen
import com.example.smartpot.ui.screens.SignupScreen
import com.example.smartpot.ui.screens.SplashScreen
import com.example.smartpot.ui.theme.SmartPotTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            App()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App() {
        val navController = rememberNavController()
        val launchViewModel: LaunchViewModel = hiltViewModel()

        LaunchedEffect(launchViewModel) {
            launchViewModel.state
                .filter { it !is LaunchState.Loading }
                .firstOrNull()
                ?.let { state ->
                    when (state) {
                        is LaunchState.SignedIn -> {
                            navController.navigate(Screen.DeviceList.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                        is LaunchState.SignedOut -> {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                        else -> {}
                    }
                }
        }

        SmartPotTheme {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text("SmartPot")
                    })
                }
            ) { innerPadding ->
                Column(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController, launchViewModel)
                        }

                        composable(Screen.DeviceList.route) {
                            DeviceListScreen(navController)
                        }

                        composable(Screen.Device.route) { backStackEntry ->
                            val idArg = backStackEntry.arguments?.getString("id")
                            val id = idArg?.toIntOrNull() ?: -1
                            DeviceScreen(navController, id)
                        }

                        composable(Screen.Signup.route) {
                            SignupScreen(navController)
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(navController)
                        }

                        composable(Screen.Splash.route) {
                            SplashScreen(navController)
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {

    }
}