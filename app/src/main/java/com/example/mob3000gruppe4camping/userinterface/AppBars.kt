package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampingTopAppBar() {
    androidx.compose.material3.TopAppBar(
        title = {
            Text("")
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = { /* burgerMenu() */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
            }
        }
    )
}

@Composable
fun CampingBottomAppBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Default.Share, contentDescription = "Share Icon")
            }
        }
    )
}