package com.vp.navigationtest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayarDetail(
    proyek: Proyek,
    namaKomunitas: String,
    onKembali: () -> Unit,
    onStatus: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Project Detail", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onKembali) {
                    Image(
                        painter = painterResource(id = R.drawable.kembali),
                        contentDescription = "Kembali",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            windowInsets = WindowInsets(0.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // kartu judul + badge komunitas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Label(namaKomunitas)
                    Text(
                        proyek.judul,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // toggle status
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Mark as Done", fontWeight = FontWeight.Bold)
                        StatusProyek(proyek.selesai)
                    }
                    Switch(
                        checked = proyek.selesai,
                        onCheckedChange = { onStatus() }
                    )
                }
            }

            // deskripsi
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Deskripsi Lengkap Proyek", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(proyek.deskripsi, fontSize = 13.sp)
                }
            }

            // tech stack
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tech Stack Terpilih", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    BarisLabel(proyek.teknologi)
                }
            }
        }
    }
}