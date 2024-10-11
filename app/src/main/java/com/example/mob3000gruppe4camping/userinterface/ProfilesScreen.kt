package com.example.mob3000gruppe4camping.userinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mob3000gruppe4camping.R
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun ProfilesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val profileImage: Painter = painterResource(id = android.R.drawable.ic_menu_camera)


        Image(
            painter = profileImage,
            contentDescription = "Profil bilde",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Jonas Deig",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "jonas.deig@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontSize = 20.sp,
        )
        Text(
            text = "123456789",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  },
            modifier = Modifier.size(200.dp, 60.dp)
        ) {
            Text(text = "Mine bookinger")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  },
            modifier = Modifier
                .size(200.dp, 60.dp)
        ) {
            Text("Logg ut")
        }

    }
}
