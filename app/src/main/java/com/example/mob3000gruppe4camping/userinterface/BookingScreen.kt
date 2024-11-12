package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.mob3000gruppe4camping.R
import com.example.mob3000gruppe4camping.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.security.Timestamp
import java.time.Instant


data class Booking(
    val campingSpot: String? = null,
    val campingType: String? = null,
    val campingDuration: String? = null,
    val userId: String? = null,
    val timestamp: String? = null,
    val antPersoner: String? = null
) {
    constructor() : this(null, null, null, null, null, null)
}

@Composable
fun BookingScreen(navController: NavHostController) {

        LaunchedEffect(key1 = FirebaseAuth.getInstance().currentUser) {
            if (FirebaseAuth.getInstance().currentUser == null) {
                navController.navigate(Screen.LoginSignup.route)
            }
        }

    var selectedCampingSpot by remember { mutableStateOf("Velg camping plass") }
    var selectedCampingType by remember { mutableStateOf("Velg camping type") }
    var selectedCampingDuration by remember { mutableStateOf("Velg hvor lenge") }
    var selectedAntPersoner by remember { mutableStateOf("Velg antall personer") }

    val campingSpots = listOf("A1", "A2", "A3", "B1")
    val campingTypes = listOf("Telt", "Camping-Vogn", "Camping-Buss")
    val campingDurations = listOf("1 Natt", "2 Netter", "3 Netter", "1 Uke")
    val antPersoner = listOf("1 Person", "2 Personer", "3 Personer", "4 Personer")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Booking",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(25.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Booking Details",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DropdownItem(
                    selectedValue = selectedCampingSpot,
                    onValueChange = { selectedCampingSpot = it },
                    items = campingSpots,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownItem(
                    selectedValue = selectedCampingType,
                    onValueChange = { selectedCampingType = it },
                    items = campingTypes,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownItem(
                    selectedValue = selectedCampingDuration,
                    onValueChange = { selectedCampingDuration = it },
                    items = campingDurations,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownItem(
                    selectedValue = selectedAntPersoner,
                    onValueChange = { selectedAntPersoner = it },
                    items = antPersoner,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        ConfirmButton(
            navController = navController,
            campingSpot = selectedCampingSpot,
            campingType = selectedCampingType,
            campingDuration = selectedCampingDuration,
            antPersoner = selectedAntPersoner
        )
    }
}

@Composable
fun ConfirmButton(
    navController: NavHostController,
    campingSpot: String,
    campingType: String,
    campingDuration: String,
    antPersoner: String
) {
    val db = FirebaseFirestore.getInstance()
    Button(
        onClick = {
            val timestamplong: Long = System.currentTimeMillis()/1000
            val timestamp = timestamplong.toString()

            val booking = Booking(
                campingSpot = campingSpot,
                campingType = campingType,
                campingDuration = campingDuration,
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                timestamp = timestamp,
                antPersoner = antPersoner
            )

            db.collection("bookings")
                .add(booking)
                .addOnSuccessListener {
                    navController.navigate(Screen.Receipt.route)
                }
                .addOnFailureListener { e ->

                    println("Error adding document: $e")
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("Bekreft")
    }
}

@Composable
fun DropdownItem(
    selectedValue: String,
    onValueChange: (String) -> Unit,
    items: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = selectedValue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
