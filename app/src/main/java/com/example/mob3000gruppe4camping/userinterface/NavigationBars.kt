package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mob3000gruppe4camping.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<Screen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
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
fun CampingTopAppBar() {
    val menuExpanded = remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text("")
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = { menuExpanded.value = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
            }
            CampingDropdownMenu(
                menuExpanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = false }
            )
        }
    )
}

@Composable
fun CampingDropdownMenu(menuExpanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Home") },
            onClick = {
                onDismissRequest()
            }
        )
    }
}