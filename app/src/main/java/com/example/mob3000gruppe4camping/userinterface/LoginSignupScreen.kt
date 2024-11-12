package com.example.mob3000gruppe4camping.userinterface


import android.util.Patterns
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
import kotlinx.coroutines.delay


@Composable
fun LoginSignupScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }

    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            delay(20000)
            errorMessage = ""
        }
    }

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
                val namePattern = Regex("^[A-Za-zÆØÅæøå ]+\$")

                if (!isLogin && name.isBlank()) {
                    errorMessage = "Vennligst fyll ut navnet ditt."
                } else if (!isLogin && !name.matches(namePattern)) {
                    errorMessage = "Navnet kan bare inneholde bokstaver og mellomrom."
                } else if (email.isBlank()) {
                    errorMessage = "Vennligst fyll ut e-postadressen."
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Vennligst skriv en gyldig e-postadresse."
                } else if (password.isBlank()) {
                    errorMessage = "Vennligst fyll ut passordet."
                } else if (password.length < 6 || !password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*])[A-Za-z\\d!@#\$%^&*]+$"))) {
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
                                    navController.navigate(Screen.Home.route)
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
