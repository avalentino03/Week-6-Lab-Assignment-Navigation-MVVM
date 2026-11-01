package com.vp.navigationtest

data class Komunitas(
    val id: String,
    val nama: String,
    val info: String,
    val deskripsi: String,
    val anggota: Int,
    val gabung: Boolean = false
)

data class Proyek(
    val id: String,
    val idKomunitas: String,
    val judul: String,
    val deskripsi: String,
    val teknologi: List<String>,
    val selesai: Boolean = false
)