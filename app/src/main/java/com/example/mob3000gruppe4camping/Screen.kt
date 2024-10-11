package com.example.mob3000gruppe4camping

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Map : Screen("map", "Maps", Icons.Default.Map)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

}

