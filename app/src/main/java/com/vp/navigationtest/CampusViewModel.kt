package com.vp.navigationtest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CampusViewModel : ViewModel() {

//dataprifil
    val namaSaya = "Aditya Pratama"
    val jurusanSaya = "Teknik Informatika - Angkatan 2022"
    val nimSaya = "NIM: 22001045"
    //komunitas
    val komunitas = mutableStateListOf(
        Komunitas("gdsc", "GDSC", "Google Developer Student Club",
            "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.", 142, true),
        Komunitas("cyber", "CyberSecurity Guild", "Security & Pentesting",
            "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.", 88, true),
        Komunitas("ai", "AI Society", "Machine Learning & LLM",
            "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.", 115, false),
        Komunitas("mobile", "Mobile Dev Community", "Jetpack Compose & KMP",
            "Native Jetpack Compose dan iOS, membangun aplikasi mobile untuk kebutuhan kampus.", 95, true),
        Komunitas("web", "Web Dev Club", "React, Laravel & Cloud",
            "Belajar membangun website modern dari frontend, backend, sampai deployment.", 76, false),
        Komunitas("data", "Data Science Club", "Analisis Data & Statistik",
            "Mengolah data, visualisasi, dan statistika memakai Python dan notebook.", 64, false)
    )

    //proyekstartnya
    val proyek = mutableStateListOf(
        Proyek("p1", "gdsc", "Smart Campus Navigation",
            "Sistem navigasi peta gedung kampus interaktif berbasis Jetpack Compose dan Google Maps SDK. Memudahkan mahasiswa dan dosen menemukan ruang kuliah.",
            listOf("Kotlin", "Jetpack Compose", "Room")),
        Proyek("p2", "gdsc", "Student Attendance QR Scanner",
            "Pemindai barcode dan QR untuk presensi kehadiran workshop berbasis CameraX dan Firebase.",
            listOf("Jetpack Compose", "Firebase"), true),
        Proyek("p3", "cyber", "Latihan CTF Kampus",
            "Kumpulan soal latihan capture the flag untuk anggota baru guild.",
            listOf("Python")),
        Proyek("p4", "mobile", "Catatan Kuliah",
            "Aplikasi catatan kuliah sederhana yang tersimpan di database lokal.",
            listOf("Kotlin", "Room"))
    )

    //form
    val pilihanTeknologi = listOf("Kotlin", "Jetpack Compose", "Firebase", "Ktor", "Room", "Python", "Maps SDK")
    var dialogTampil by mutableStateOf(false)
    var judulForm by mutableStateOf("")
    var deskripsiForm by mutableStateOf("")
    val teknologiForm = mutableStateListOf<String>()
    val formSiap: Boolean
        get() = judulForm.isNotBlank() && deskripsiForm.isNotBlank() && teknologiForm.isNotEmpty()

    fun ubahGabung(id: String) {
        val urutan = komunitas.indexOfFirst { it.id == id }
        if (urutan >= 0) {
            val lama = komunitas[urutan]
            komunitas[urutan] = lama.copy(gabung = !lama.gabung)
        }
    }

    fun ubahStatus(id: String) {
        val urutan = proyek.indexOfFirst { it.id == id }
        if (urutan >= 0) {
            val lama = proyek[urutan]
            proyek[urutan] = lama.copy(selesai = !lama.selesai)
        }
    }

    fun hapus(id: String) {
        proyek.removeAll { it.id == id }
    }

    fun pilih(tek: String) {
        if (teknologiForm.contains(tek)) {
            teknologiForm.remove(tek)
        } else {
            teknologiForm.add(tek)
        }
    }

    fun bukaDialog() {
        dialogTampil = true
    }

    fun tutupDialog() {
        dialogTampil = false
        judulForm = ""
        deskripsiForm = ""
        teknologiForm.clear()
    }

    fun simpan(idKomunitas: String) {
        proyek.add(
            Proyek(
                id = "proj-" + System.currentTimeMillis(),
                idKomunitas = idKomunitas,
                judul = judulForm,
                deskripsi = deskripsiForm,
                teknologi = teknologiForm.toList()
            )
        )
        tutupDialog()
    }
}