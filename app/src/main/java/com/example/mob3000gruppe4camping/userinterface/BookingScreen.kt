package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.mob3000gruppe4camping.R

@Composable
fun BookingScreen() {

    var selectedCampingSpot by remember { mutableStateOf("Velg camping plass") }
    var selectedCampingType by remember { mutableStateOf("Velg camping type") }
    var selectedCampingDuration by remember { mutableStateOf("Velg hvor lenge") }

    val campingSpots = listOf("A1", "A2", "A3", "B1")
    val campingTypes = listOf("Telt", "Camping-Vogn", "Camping-Buss")
    val campingDurations = listOf("1 Natt", "2 Netter", "3 Netter", "1 Uke")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CampingPlassImage()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Booking",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(25.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Booking Details",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DropdownItem(
                    selectedValue = selectedCampingSpot,
                    onValueChange = { selectedCampingSpot = it },
                    items = campingSpots,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownItem(
                    selectedValue = selectedCampingType,
                    onValueChange = { selectedCampingType = it },
                    items = campingTypes,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownItem(
                    selectedValue = selectedCampingDuration,
                    onValueChange = { selectedCampingDuration = it },
                    items = campingDurations,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Booking recepit side */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Bekreft")
        }
    }
}

@Composable
fun CampingPlassImage() {
    Image(
        painter = painterResource(id = R.drawable.campingoversikt3), // Replace with your image resource
        contentDescription = "Camping Logo",
        modifier = Modifier.size(200.dp) // Adjust size if necessary
    )
}

@Composable
fun DropdownItem(
    selectedValue: String,
    onValueChange: (String) -> Unit,
    items: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = selectedValue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
