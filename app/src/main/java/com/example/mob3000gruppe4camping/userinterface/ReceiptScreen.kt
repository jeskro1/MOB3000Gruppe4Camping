package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.geometry.isEmpty
import androidx.lifecycle.get
import androidx.navigation.NavHostController
import com.example.mob3000gruppe4camping.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

@Composable
fun ReceiptScreen(navController: NavHostController) {
    var bookingData by remember { mutableStateOf<Booking?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<Exception?>(null) }

    LaunchedEffect(key1 = FirebaseAuth.getInstance().currentUser) {
        if (FirebaseAuth.getInstance().currentUser == null) {
            navController.navigate(Screen.LoginSignup.route)
        } else {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val db = Firebase.firestore

            db.collection("bookings")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        bookingData = querySnapshot.documents[0].toObject(Booking::class.java)
                    }
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
            text = "Kvittering",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Plass: ${bookingData?.campingSpot ?: ""}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Type: ${bookingData?.campingType ?: ""}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Tid: ${bookingData?.campingDuration ?: ""}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Personer: ${bookingData?.antPersoner ?: ""}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Dato: 09-10-2024",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Pris: 299,99kr",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
