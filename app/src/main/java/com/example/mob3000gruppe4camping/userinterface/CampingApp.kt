package com.example.mob3000gruppe4camping.userinterface

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
            composable(Screen.Map.route) {OpenGoogleMapsLocation()}
            composable(Screen.Profile.route) { ProfilesScreen(navController) }
            composable(Screen.Booking.route) { BookingScreen(navController) }
            composable(Screen.Receipt.route) { ReceiptScreen() }
            composable(Screen.MineBookinger.route) { MineBookingerScreen() }
        }
    }
}

@Composable
fun OpenGoogleMapsLocation() {
    val context = LocalContext.current
    val latitude = 48.8584
    val longitude = 2.2945

    val uri = Uri.parse("geo:$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

