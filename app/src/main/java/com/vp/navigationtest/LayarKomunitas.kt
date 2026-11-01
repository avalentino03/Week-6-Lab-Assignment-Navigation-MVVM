package com.vp.navigationtest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LayarKomunitas(
    daftar: List<Komunitas>,
    onGabung: (String) -> Unit,
    onBuka: (Komunitas) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text("Communities Directory", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Pilih komunitas untuk membuka Club Projects", fontSize = 12.sp)
            }
        }

        items(daftar, key = { it.id }) { k ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBuka(k) }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(k.nama, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("${k.info} - ${k.anggota} Mahasiswa", fontSize = 12.sp)
                    Text(k.deskripsi, fontSize = 13.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (k.gabung) {
                            Text("Unlocked - Buka Proyek", fontSize = 12.sp)
                            OutlinedButton(
                                onClick = { onGabung(k.id) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                            ) {
                                Text("Joined")
                            }
                        } else {
                            Text("Terkunci (Gate Akses)", fontSize = 12.sp)
                            Button(onClick = { onGabung(k.id) }) {
                                Text("Join")
                            }
                        }
                    }
                }
            }
        }
    }
}