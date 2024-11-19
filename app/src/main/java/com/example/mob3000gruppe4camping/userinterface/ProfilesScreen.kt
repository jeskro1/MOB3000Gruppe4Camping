package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mob3000gruppe4camping.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfilesScreen(navController: NavHostController) {

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val userEmail = firebaseUser?.email ?: ""
    val name = firebaseUser?.displayName ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Navn: $name",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Email: $userEmail",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        MineBookingerButton(navController)


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { FirebaseAuth.getInstance().signOut()
                navController.navigate(Screen.LoginSignup.route) },
            modifier = Modifier
                .size(200.dp, 60.dp)
        ) {
            Text("Logg ut")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { FirebaseAuth.getInstance().currentUser?.delete() // Warning?
                navController.navigate(Screen.LoginSignup.route) },
            modifier = Modifier
                .size(200.dp, 60.dp)
        ) {
            Text("Slett bruker")
        }
    }
}

@Composable
fun MineBookingerButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate(Screen.MineBookinger.route)
        },
        modifier = Modifier
            .size(200.dp, 60.dp)
    ) {
        Text("Mine bookinger")
    }
}
