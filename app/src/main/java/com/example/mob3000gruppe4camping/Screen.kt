package com.example.mob3000gruppe4camping

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object LoginSignup : Screen("loginsignup", "LoginSignUp", Icons.Default.LockPerson)
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Map : Screen("map", "Maps", Icons.Default.Map)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Booking : Screen("booking", "Booking", Icons.Default.Book)
    object Receipt : Screen("receipt", "Receipt", Icons.Default.Receipt)
    object MineBookinger : Screen("minebookinger", "Mine Bookinger", Icons.Default.Receipt)
}