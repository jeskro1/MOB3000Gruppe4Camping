package com.example.mob3000gruppe4camping.userinterface

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.mob3000gruppe4camping.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import java.text.SimpleDateFormat
import java.util.*

// Data klasse for booking objekt
data class Booking(
    val bookingID: String? = null,
    val campingSpot: String? = null,
    val campingType: String? = null,
    val userId: String? = null,
    val timestamp: String? = null,
    val antPersoner: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val pris: Int? = null
)

@SuppressLint("SimpleDateFormat") // Suppress warning for SimpleDateFormat
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(navController: NavHostController) {

        // Sjekker om bruker er logget inn
        LaunchedEffect(key1 = FirebaseAuth.getInstance().currentUser) {
            if (FirebaseAuth.getInstance().currentUser == null) {
                navController.navigate(Screen.LoginSignup.route)
            }
        }

    var startDateButtonText by remember { mutableStateOf("Select Start Date") }
    var endDateButtonText by remember { mutableStateOf("Select End Date") }
    var selectedCampingSpot by remember { mutableStateOf("Velg camping plass") }
    var selectedCampingType by remember { mutableStateOf("Velg camping type") }
    var selectedAntPersoner by remember { mutableStateOf("Velg antall personer") }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val campingSpots = listOf("A1", "A2", "A3", "B1")
    val campingTypes = listOf("Telt", "Camping-Vogn", "Camping-Buss")
    val antPersoner = listOf("1", "2", "3", "4")
    val datePickerStateStart = rememberDatePickerState()
    val datePickerStateEnd = rememberDatePickerState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // DatePicker for startdato
        if (showStartDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showStartDatePicker = false
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                        startDateButtonText = dateFormat.format(datePickerStateStart.selectedDateMillis)
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showStartDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerStateStart)
            }
        }

        // DatePicker for sluttdato
        if (showEndDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showEndDatePicker = false
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                        endDateButtonText = dateFormat.format(datePickerStateEnd.selectedDateMillis)
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerStateEnd)
            }
        }

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
                    selectedValue = selectedAntPersoner,
                    onValueChange = { selectedAntPersoner = it },
                    items = antPersoner,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { showStartDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(startDateButtonText)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { showEndDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(endDateButtonText)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        ConfirmButton(
            navController = navController,
            campingSpot = selectedCampingSpot,
            campingType = selectedCampingType,
            antPersoner = selectedAntPersoner,
            startDate = datePickerStateStart.selectedDateMillis,
            endDate = datePickerStateEnd.selectedDateMillis
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ConfirmButton(
    navController: NavHostController,
    campingSpot: String,
    campingType: String,
    antPersoner: String,
    startDate: Long?,
    endDate: Long?
) {
    val price = if (
        campingSpot != "Velg camping plass" &&
        campingType != "Velg camping type" &&
        antPersoner != "Velg antall personer" &&
        startDate != null &&
        endDate != null
    ) {
        calculatePrice(
            campingSpot,
            campingType,
            antPersoner.toIntOrNull() ?: 1,
            startDate,
            endDate
        )
    } else {
        0.0
    }

    val db = FirebaseFirestore.getInstance()
    var errorMessage by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (price > 0) {
            Text(
                text = "Totalpris: ${price.toInt()} kr",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }

        // Bekreft button
        Button(
            onClick = {
                // Error handling
                if (campingSpot == "Velg camping plass") {
                    errorMessage = "Vennligst velg en campingplass."
                } else if (campingType == "Velg camping type") {
                    errorMessage = "Vennligst velg en campingtype."
                } else if (antPersoner == "Velg antall personer") {
                    errorMessage = "Vennligst velg antall personer."
                } else if (startDate == null) {
                    errorMessage = "Vennligst velg en startdato."
                } else if (endDate == null) {
                    errorMessage = "Vennligst velg en sluttdato."
                } else if (endDate <= startDate) {
                    errorMessage = "Sluttdato må være etter startdato."
                } else {
                    val timestampLong: Long = System.currentTimeMillis() / 1000
                    val timestamp = timestampLong.toString()
                    val bookingID = UUID.randomUUID().toString()

                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val startDateString = dateFormat.format(Date(startDate))
                    val endDateString = dateFormat.format(Date(endDate))

                    val booking = Booking(
                        bookingID = bookingID,
                        campingSpot = campingSpot,
                        campingType = campingType,
                        userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                        timestamp = timestamp,
                        antPersoner = antPersoner,
                        startDate = startDateString,
                        endDate = endDateString,
                        pris = price.toInt()
                    )

                    db.collection("bookings")
                        .add(booking)
                        .addOnSuccessListener {
                            navController.navigate(Screen.Receipt.route)
                        }
                        .addOnFailureListener { e ->
                            errorMessage = "Noe gikk galt: ${e.localizedMessage}"
                        }
                    errorMessage = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Bekreft")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Kalkulerer "placeholder" pris
fun calculatePrice(campingSpot: String, campingType: String, antPersoner: Int, startDate: Long, endDate: Long): Double {
    val basePrice = when (campingType) {
        "Telt" -> 100.0
        "Camping-Vogn" -> 200.0
        "Camping-Buss" -> 300.0
        else -> 50.0
    }
    val locationMultiplier = when (campingSpot) {
        "A1", "A2" -> 1.1
        "B1", "B2" -> 1.2
        else -> 1.0
    }
    val antDager = (endDate - startDate) / (1000 * 60 * 60 * 24)

    return basePrice * locationMultiplier * antDager * antPersoner
}

// Funksjon for dropdown valgene
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
