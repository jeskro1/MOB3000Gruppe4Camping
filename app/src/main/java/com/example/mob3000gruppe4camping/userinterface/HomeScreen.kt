package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mob3000gruppe4camping.R

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CampingImage()
            Spacer(modifier = Modifier.height(32.dp))
            TitleText()
            Spacer(modifier = Modifier.height(32.dp))
            CenteredButton()
        }
    }
}

@Composable
fun CampingImage() {
    Image(
        painter = painterResource(id = R.drawable.camping_zone_1), // Replace with your image resource
        contentDescription = "Camping Logo",
        modifier = Modifier.size(200.dp) // Adjust size if necessary
    )
}

@Composable
fun TitleText() {
    Text(
        text = "Camping",
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun CenteredButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {  },
        modifier = Modifier
            .size(200.dp, 60.dp)
    ) {
        Text("Bestill plass nå!")
    }
}