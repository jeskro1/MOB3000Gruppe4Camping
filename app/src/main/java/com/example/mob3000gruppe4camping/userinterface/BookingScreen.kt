package com.example.mob3000gruppe4camping.userinterface

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
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



data class Booking(
    val bookingID: String? = null,
    val campingSpot: String? = null,
    val campingType: String? = null,
    val userId: String? = null,
    val timestamp: String? = null,
    val antPersoner: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(navController: NavHostController) {

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

    val bookingID = UUID.randomUUID().toString()
    val campingSpots = listOf("A1", "A2", "A3", "B1")
    val campingTypes = listOf("Telt", "Camping-Vogn", "Camping-Buss")
    val antPersoner = listOf("1", "2", "3", "4")
    val datePickerStateStart = rememberDatePickerState()
    val datePickerStateEnd = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
    val db = FirebaseFirestore.getInstance()
    var errorMessage by remember { mutableStateOf("") }

    Button(
        onClick = {

            if (campingSpot == "Velg camping plass" ||
                campingType == "Velg camping type" ||
                antPersoner == "Velg antall personer" ||
                startDate == null ||
                endDate == null
            ) {
                errorMessage = "Vennligst fyll ut alle feltene før du bekrefter."
            } else {
                val timestampLong: Long = System.currentTimeMillis() / 1000
                val timestamp = timestampLong.toString()
                val bookingID = UUID.randomUUID().toString()

                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
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
                    endDate = endDateString
                )

                db.collection("bookings")
                    .add(booking)
                    .addOnSuccessListener {
                        navController.navigate(Screen.Receipt.route)
                    }
                    .addOnFailureListener { e ->
                        println("Error adding document: $e")
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
