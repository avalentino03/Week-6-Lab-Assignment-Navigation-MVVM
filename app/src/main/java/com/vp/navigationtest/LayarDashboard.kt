package com.vp.navigationtest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LayarDashboard(
    nama: String,
    jurusan: String,
    nim: String,
    daftarKomunitas: List<Komunitas>,
    daftarProyek: List<Proyek>
) {
    val diikuti = daftarKomunitas.filter { it.gabung }
    val inisial = nama.split(" ").map { it.first() }.joinToString("")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text("CampusTech Hub", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Student Profile & Portfolio Dashboard", fontSize = 12.sp)
            }
        }
        //karu
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1D4ED8))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(inisial, color = Color(0xFF1D4ED8), fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("MAHASISWA AKTIF", color = Color.White, fontSize = 10.sp)
                        Text(nama, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(jurusan, color = Color.White, fontSize = 12.sp)
                        Text(nim, color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }

        // dua counter
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${diikuti.size}", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text("Joined Clubs", fontWeight = FontWeight.Bold)
                        Text("Komunitas yang Diikuti", fontSize = 11.sp)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${daftarProyek.size}", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text("Total Projects", fontWeight = FontWeight.Bold)
                        Text("Seluruh Proyek Dibuat", fontSize = 11.sp)
                    }
                }
            }
        }
        item {
            Text(
                "Semua Komunitas yang Diikuti (${diikuti.size})",
                fontWeight = FontWeight.Bold
            )
        }
        if (diikuti.isEmpty()) {
            item { Text("Belum bergabung dengan komunitas") }
        } else {
            items(diikuti, key = { it.id }) { k ->
                val jumlah = daftarProyek.count { it.idKomunitas == k.id }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(k.nama, fontWeight = FontWeight.Bold)
                            Text("${k.info} - $jumlah Proyek", fontSize = 12.sp)
                        }
                        Label("Joined")
                    }
                }
            }
        }

        // daftarproyek
        item {
            Text(
                "Semua Proyek Mahasiswa (${daftarProyek.size})",
                fontWeight = FontWeight.Bold
            )
        }
        if (daftarProyek.isEmpty()) {
            item { Text("Belum ada proyek yang dicatat") }
        } else {
            items(daftarProyek, key = { it.id }) { p ->
                val namaKomunitas = daftarKomunitas.find { it.id == p.idKomunitas }?.nama ?: ""
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Label(namaKomunitas)
                        Text(p.judul, fontWeight = FontWeight.Bold)
                        BarisLabel(p.teknologi)
                        StatusProyek(p.selesai)
                    }
                }
            }
        }
    }
}