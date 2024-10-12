package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mob3000gruppe4camping.Screen
import com.example.mob3000gruppe4camping.R // Import your logo and icons here

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController, homeScreenRoute: String, onBack: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    TopAppBar(
        title = {},
        navigationIcon = {
            if (currentRoute == homeScreenRoute) {
                Box(
                    modifier = Modifier
                        .size(175.dp)
                        .clickable(onClick = {
                        })
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.camping1),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {

                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.backspace),
                        contentDescription = "Go Back"
                    )
                }
            }
        },
        actions = {
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu",
                    modifier = Modifier.size(30.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Option 1") },
                    onClick = { /* Option 1 */ }
                )
                DropdownMenuItem(
                    text = { Text("Option 2") },
                    onClick = { /* Option 2 */ }
                )
            }
        }
    )
}
*/