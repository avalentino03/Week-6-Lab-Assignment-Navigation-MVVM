package com.vp.navigationtest

import com.vp.navigationtest.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayarProyek(
    komunitas: Komunitas,
    daftar: List<Proyek>,
    onKembali: () -> Unit,
    onBuka: (String) -> Unit,
    onHapus: (String) -> Unit,
    onTambah: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Column {
                        Text("${komunitas.nama} Projects", fontWeight = FontWeight.Bold)
                        Text(komunitas.info, fontSize = 12.sp)
                    }
                },
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

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Daftar Proyek Mahasiswa (${daftar.size})", fontWeight = FontWeight.Bold)
                }
                if (daftar.isEmpty()) {
                    item { Text("Belum ada proyek di komunitas ini") }
                }
                items(daftar, key = { it.id }) { p ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onBuka(p.id) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(p.judul, fontWeight = FontWeight.Bold)
                                Text(p.deskripsi, fontSize = 12.sp, maxLines = 2)
                                BarisLabel(p.teknologi)
                                StatusProyek(p.selesai)
                            }
                            IconButton(onClick = { onHapus(p.id) }) {
                                Image(
                                    painter = painterResource(id = R.drawable.hapus),
                                    contentDescription = "Hapus",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onTambah,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.tambah),
                contentDescription = "Tambah Proyek",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DialogProyek(
    namaKomunitas: String,
    judul: String,
    deskripsi: String,
    pilihan: List<String>,
    terpilih: List<String>,
    siap: Boolean,
    onJudul: (String) -> Unit,
    onDeskripsi: (String) -> Unit,
    onPilih: (String) -> Unit,
    onSimpan: () -> Unit,
    onBatal: () -> Unit
) {
    AlertDialog(
//dialogga bsia di cancel
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        title = { Text("Add Project") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Komunitas: $namaKomunitas", fontSize = 12.sp)

                OutlinedTextField(
                    value = judul,
                    onValueChange = onJudul,
                    label = { Text("Judul Proyek *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = deskripsi,
                    onValueChange = onDeskripsi,
                    label = { Text("Deskripsi Proyek (min 3 baris) *") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Tech Stack (pilih lebih dari satu) *", fontWeight = FontWeight.Bold)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pilihan.forEach { tek ->
                        FilterChip(
                            selected = terpilih.contains(tek),
                            onClick = { onPilih(tek) },
                            label = { Text(tek) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onSimpan, enabled = siap) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onBatal) {
                Text("Cancel")
            }
        }
    )
}