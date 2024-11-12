package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mob3000gruppe4camping.Screen
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

@Composable
fun MineBookingerScreen(navController: NavHostController) {

    var bookings by remember { mutableStateOf<List<Booking>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<Exception?>(null) }

    LaunchedEffect(key1 = FirebaseAuth.getInstance().currentUser)
    {
        if (FirebaseAuth.getInstance().currentUser == null) {
            navController.navigate(Screen.LoginSignup.route)
        } else {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val db = Firebase.firestore
            db.collection("bookings")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    bookings = querySnapshot.toObjects(Booking::class.java)
                    isLoading = false
                }
                .addOnFailureListener { exception ->
                    error = exception
                    isLoading = false
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Alle kvitteringer",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Show loading spinner if data is being fetched
        if (isLoading) {
            CircularProgressIndicator()
        }
        // Show error message if there's an issue fetching data
        else if (error != null) {
            Text("Error: ${error?.message}")
        }
        // Show bookings if data is loaded
        else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookings) { booking ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            // Display each piece of booking information
                            Text(
                                text = "Sted: ${booking.campingSpot ?: "N/A"}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Type: ${booking.campingType ?: "N/A"}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Personer: ${booking.antPersoner ?: "N/A"}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            // Display dates if available
                            Text(
                                text = "Start Dato: ${booking.startDate ?: "N/A"}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Slutt Dato: ${booking.endDate ?: "N/A"}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            // Example price (you can calculate or replace it with actual data)
                            Text(
                                text = "Pris: 299,99kr",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
