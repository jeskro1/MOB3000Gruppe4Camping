package com.example.mob3000gruppe4camping.userinterface


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mob3000gruppe4camping.R
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
                        // Trigger the Google Maps intent instead of navigating
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