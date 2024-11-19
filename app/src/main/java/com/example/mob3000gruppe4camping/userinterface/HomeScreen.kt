package com.example.mob3000gruppe4camping.userinterface

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mob3000gruppe4camping.R
import com.example.mob3000gruppe4camping.Screen

// Hovedskjerm for appen
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CampingImage()
            Spacer(modifier = Modifier.height(32.dp))
            TitleText()
            Spacer(modifier = Modifier.height(32.dp))
            CenteredButton(navController)
        }
    }
}

// Funksjoner for bilde, tekst og knapp på hovedskjermen
@Composable
fun CampingImage() {
    Image(
        painter = painterResource(id = R.drawable.camping_zone_1),
        contentDescription = "Camping Logo",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun TitleText() {
    Text(
        text = "Camping",
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun CenteredButton(navController: NavHostController) {
    Button(
        onClick = {
            Log.d("Navigation", "Navigating to route: ${Screen.Booking.route}")
            navController.navigate(Screen.Booking.route)
        },
        modifier = Modifier.size(200.dp, 60.dp)
    ) {
        Text("Bestill plass nå!")
    }
}
