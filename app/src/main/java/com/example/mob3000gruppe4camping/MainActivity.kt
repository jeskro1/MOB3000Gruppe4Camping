package com.example.mob3000gruppe4camping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mob3000gruppe4camping.ui.theme.MOB3000Gruppe4CampingTheme
import com.example.mob3000gruppe4camping.userinterface.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MOB3000Gruppe4CampingTheme {
                HomeScreen()
            }
        }
    }
}

