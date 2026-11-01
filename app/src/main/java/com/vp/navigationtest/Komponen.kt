package com.vp.navigationtest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun Label(teks: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = teks,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BarisLabel(daftar: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        daftar.forEach { Label(it) }
    }
}
@Composable
fun StatusProyek(selesai: Boolean) {
    val teks = if (selesai) "Completed" else "In Progress"
    val warna = if (selesai) Color(0xFFC8E6C9) else Color(0xFFFFE0B2)
    Surface(shape = RoundedCornerShape(50), color = warna) {
        Text(
            text = teks,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}