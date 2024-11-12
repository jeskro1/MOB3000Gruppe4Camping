package com.example.mob3000gruppe4camping.userinterface

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {



    private lateinit var db: FirebaseFirestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "sampleUserId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        db = FirebaseFirestore.getInstance()


        createUserProfile()
        readUserProfile()
        updateUserProfile()
        deleteUserProfile()

        setContent {
            MOB3000Gruppe4CampingTheme {
                val navController = rememberNavController()
                CampingApp(navController) {
                    openGoogleMaps()
                }
            }
        }
    }


    private fun openGoogleMaps() {
        val latitude = 59.5641
        val longitude = 9.6015
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(Campingplass)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    // CRUD functions
    private fun createUserProfile() {
        val userData = hashMapOf("name" to "John Doe", "email" to "johndoe@example.com")

        db.collection("users").document(userId)
            .set(userData)
            .addOnSuccessListener { Log.d("FirestoreCRUD", "Document successfully written!") }
            .addOnFailureListener { e -> Log.w("FirestoreCRUD", "Error writing document", e) }
    }

    private fun readUserProfile() {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("FirestoreCRUD", "Document data: ${document.data}")
                } else {
                    Log.d("FirestoreCRUD", "No such document")
                }
            }
            .addOnFailureListener { e -> Log.w("FirestoreCRUD", "Error reading document", e) }
    }

    private fun updateUserProfile() {
        val updates = hashMapOf<String, Any>("email" to "newemail@example.com")

        db.collection("users").document(userId)
            .update(updates)
            .addOnSuccessListener { Log.d("FirestoreCRUD", "Document successfully updated!") }
            .addOnFailureListener { e -> Log.w("FirestoreCRUD", "Error updating document", e) }
    }

    private fun deleteUserProfile() {
        db.collection("users").document(userId)
            .delete()
            .addOnSuccessListener { Log.d("FirestoreCRUD", "Document successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FirestoreCRUD", "Error deleting document", e) }
    }
}

@Composable
fun CampingApp(navController: NavHostController, onMapSelected: () -> Unit) {
    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        Screen.Home.route
    } else {
        Screen.LoginSignup.route
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = listOf(Screen.Home, Screen.Map, Screen.Profile),
                onMapSelected = onMapSelected
            )
        }
    ) { innerPadding ->
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
