package com.example.mob3000gruppe4camping.userinterface

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopNavigationBar(
                navController = navController,
                homeScreenRoute = Screen.Home.route,
                onBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = items,
                onMapSelected = {

                    val latitude = 59.5641
                    val longitude = 9.6015
                    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(Campingplass)")
                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                        setPackage("com.google.android.apps.maps")
                    }


                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        println("Google Maps app is not available.")
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Profile.route) { ProfilesScreen(navController) }
            composable(Screen.Booking.route) { BookingScreen(navController) }
            composable(Screen.Receipt.route) { ReceiptScreen() }
            composable(Screen.MineBookinger.route) { MineBookingerScreen() }
        }
    }
}
