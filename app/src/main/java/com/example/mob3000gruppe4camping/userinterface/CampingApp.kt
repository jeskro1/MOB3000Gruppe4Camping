package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mob3000gruppe4camping.Screen

@Composable
fun CampingApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Map, Screen.Profile)

    Scaffold(
        topBar = {
            TopNavigationBar(
                navController = navController,
                homeScreenRoute = Screen.Home.route,
                onBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Map.route) {MapScreen()}
            composable(Screen.Profile.route) { ProfilesScreen() }
            composable(Screen.Booking.route) { BookingScreen() }
        }
    }
}
