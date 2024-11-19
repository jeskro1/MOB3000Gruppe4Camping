package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mob3000gruppe4camping.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

@Composable
fun LoginSignupScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (!isLogin) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Navn") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Passord") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val namePattern = Regex("^[\\p{L}\\s]+\$")
                val emailPattern = Regex("^[\\p{L}0-9._%+-]+@[\\p{L}0-9.-]+\\.[a-zA-Z]{2,}\$")
                val passwordPattern = Regex("^(?=.*[A-Za-zÆØÅæøå])(?=.*\\d)(?=.*[!@#\$%^&*])[\\p{L}\\d!@#\$%^&*]{6,}\$")

                if (!isLogin && name.isBlank()) {
                    errorMessage = "Vennligst fyll ut navnet ditt."
                } else if (!isLogin && !name.matches(namePattern)) {
                    errorMessage = "Navnet kan bare inneholde bokstaver og mellomrom."
                } else if (email.isBlank()) {
                    errorMessage = "Vennligst fyll ut e-postadressen."
                } else if (!email.matches(emailPattern)) {
                    errorMessage = "Vennligst skriv en gyldig e-postadresse."
                } else if (password.isBlank()) {
                    errorMessage = "Vennligst fyll ut passordet."
                } else if (!password.matches(passwordPattern)) {
                    errorMessage = "Passordet må ha minst 6 tegn og inneholde bokstaver, tall og spesialtegn."
                } else {
                    if (isLogin) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate(Screen.Home.route)
                                } else {
                                    errorMessage = "Feil e-postadresse eller passord. Vennligst prøv igjen."
                                }
                            }
                    } else {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = FirebaseAuth.getInstance().currentUser
                                    if (user != null) {
                                        val profileUpdate = userProfileChangeRequest {
                                            displayName = name
                                        }
                                        user.updateProfile(profileUpdate)
                                            .addOnCompleteListener { profileTask ->
                                                if (profileTask.isSuccessful) {
                                                    navController.navigate(Screen.Home.route)
                                                } else {
                                                    errorMessage = "Registrering mislyktes. Vennligst prøv igjen."
                                                }
                                                }
                                    }
                                } else {
                                    errorMessage = "Registrering mislyktes. Passordet må ha minst 6 tegn og inneholde bokstaver, tall og spesialtegn."
                                }
                            }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLogin) "Logg inn" else "Lag bruker")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "Har du ikke bruker? Lag en her!" else "Har du allerede en bruker? Logg inn her!")
        }
    }
}
