package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun MineBookingerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kvittering",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Booking ID: 12345",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Navn: Jonas Deig",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Sted: Seljord",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Dato: 09-10-2024",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Pris: 299,99kr",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Booking ID: 54321",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Navn: Jonas Deig",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Sted: Seljord",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Dato: 09-10-2023",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Pris: 2000kr",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    Spacer(modifier = Modifier.height(16.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Booking ID: 56437",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Navn: Jonas Deig",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Sted: Seljord",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Dato: 09-10-2022",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Pris: 1000kr",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
}



