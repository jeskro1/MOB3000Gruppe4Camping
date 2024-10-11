package com.example.mob3000gruppe4camping.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mob3000gruppe4camping.ui.theme.MOB3000Gruppe4CampingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MOB3000Gruppe4CampingTheme {
                CampingApp()
            }
        }
    }
}
