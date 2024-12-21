package com.example.ucp2.ui.view.dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeMenuView(
    onDosenClick: () -> Unit,
    onMatkulClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1E1FF)) // Background ungu soft
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Mengatur elemen di tengah secara vertikal
        horizontalAlignment = Alignment.CenterHorizontally // Mengatur elemen di tengah secara horizontal
    ) {
        // Tombol Menu Dosen
        Button(
            onClick = onDosenClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f) // Mengatur lebar tombol menjadi 80% dari layar
                .graphicsLayer(
                    // Efek zoom
                    scaleX = 1f,
                    scaleY = 1f
                )
                .shadow(10.dp, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)) // Efek bayangan
        ) {
            Text(
                text = "Menu Dosen",
                fontSize = 20.sp, // Memperbesar ukuran teks
                fontWeight = FontWeight.Bold, // Menambah ketebalan teks
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp)) // Jarak antar tombol

        // Tombol Menu Matakuliah
        Button(
            onClick = onMatkulClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f) // Mengatur lebar tombol menjadi 80% dari layar
                .graphicsLayer(
                    // Efek zoom
                    scaleX = 1f,
                    scaleY = 1f
                )
                .shadow(10.dp, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)) // Efek bayangan
        ) {
            Text(
                text = "Menu Matakuliah",
                fontSize = 20.sp, // Memperbesar ukuran teks
                fontWeight = FontWeight.Bold, // Menambah ketebalan teks
                color = Color.White
            )
        }
    }
}
