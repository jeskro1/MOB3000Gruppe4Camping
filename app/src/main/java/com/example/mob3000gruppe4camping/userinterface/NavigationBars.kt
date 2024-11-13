package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mob3000gruppe4camping.Screen

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Screen>,
    onMapSelected: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (screen == Screen.Map) {
                        onMapSelected()
                    } else if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController, currentRoute: String) {
    TopAppBar(
        title = { Text("") },

        navigationIcon = {
            if (currentRoute != Screen.Home.route) {
                IconButton(onClick = {
                    navController.popBackStack()
                    navController.clearBackStack(Screen.Home.route)
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                        )
                }
            }
        }
    )
}