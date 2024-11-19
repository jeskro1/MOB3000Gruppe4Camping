package com.example.mob3000gruppe4camping.userinterface

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mob3000gruppe4camping.Screen
import com.example.mob3000gruppe4camping.ui.theme.MOB3000Gruppe4CampingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MOB3000Gruppe4CampingTheme {
                val navController = rememberNavController()
                CampingApp(navController) {
                    openGoogleMaps()
                }
            }
        }
    }

    // Åpner google maps på spesifikk lokasjon
    private fun openGoogleMaps() {
        val latitude = -54.4221061
        val longitude = 3.3406971
        val placeName = "Bouvet Øya Camping"
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($placeName)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}

@Composable
fun CampingApp(navController: NavHostController, onMapSelected: () -> Unit) {
    //Setter opp skjelletet til appen
    Scaffold(
        // Setter opp topbar og bottombar
        topBar = {
            TopNavigationBar(navController)
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = listOf(Screen.Home, Screen.Map, Screen.Profile),
                onMapSelected = onMapSelected
            )
        }
    ) { innerPadding ->
        // Navigasjon mellom skjermene
        NavHost(
            navController = navController,
            startDestination = Screen.LoginSignup.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.LoginSignup.route) { LoginSignupScreen(navController) }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Receipt.route) { ReceiptScreen(navController) }
            composable(Screen.Profile.route) { ProfilesScreen(navController) }
            composable(Screen.Booking.route) { BookingScreen(navController) }
            composable(Screen.MineBookinger.route) { MineBookingerScreen(navController) }
            composable(Screen.Map.route) {
                onMapSelected()
            }
        }
    }
}
