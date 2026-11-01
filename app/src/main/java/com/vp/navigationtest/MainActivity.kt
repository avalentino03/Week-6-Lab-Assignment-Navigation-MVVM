package com.vp.navigationtest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Aplikasi()
            }
        }
    }
}

// pindah tab tanpa mereset state (daftar, scroll)
fun pindahTab(nav: NavHostController, rute: Any) {
    nav.navigate(rute) {
        popUpTo(nav.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun Aplikasi() {
    val nav = rememberNavController()
    val vm: CampusViewModel = viewModel()
    val context = LocalContext.current

    val posisi by nav.currentBackStackEntryAsState()
    val tujuan = posisi?.destination
    val diDashboard = tujuan?.hasRoute(DashboardRoute::class) == true
    val diKomunitas = tujuan?.hasRoute(CommunitiesRoute::class) == true

    Scaffold(
        bottomBar = {
            if (diDashboard || diKomunitas) {
                NavigationBar {
                    NavigationBarItem(
                        selected = diDashboard,
                        onClick = { pindahTab(nav, DashboardRoute) },
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.rumah),
                                contentDescription = "Dashboard",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Dashboard") }
                    )
                    NavigationBarItem(
                        selected = diKomunitas,
                        onClick = { pindahTab(nav, CommunitiesRoute) },
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.orang),
                                contentDescription = "Communities",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Communities") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(padding)
        ) {

            composable<DashboardRoute> {
                LayarDashboard(
                    nama = vm.namaSaya,
                    jurusan = vm.jurusanSaya,
                    nim = vm.nimSaya,
                    daftarKomunitas = vm.komunitas,
                    daftarProyek = vm.proyek
                )
            }

            composable<CommunitiesRoute> {
                LayarKomunitas(
                    daftar = vm.komunitas,
                    onGabung = { id -> vm.ubahGabung(id) },
                    onBuka = { k ->
                        if (k.gabung) {
                            nav.navigate(ClubProjectsRoute(k.id))
                        } else {
                            Toast.makeText(
                                context,
                                "Harap bergabung ke komunitas terlebih dahulu!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }

            composable<ClubProjectsRoute> { entri ->
                val rute = entri.toRoute<ClubProjectsRoute>()
                val k = vm.komunitas.find { it.id == rute.communityId }

                if (k != null) {
                    LayarProyek(
                        komunitas = k,
                        daftar = vm.proyek.filter { it.idKomunitas == k.id },
                        onKembali = { nav.popBackStack() },
                        onBuka = { id -> nav.navigate(ProjectDetailRoute(id)) },
                        onHapus = { id -> vm.hapus(id) },
                        onTambah = { vm.bukaDialog() }
                    )

                    if (vm.dialogTampil) {
                        DialogProyek(
                            namaKomunitas = k.nama,
                            judul = vm.judulForm,
                            deskripsi = vm.deskripsiForm,
                            pilihan = vm.pilihanTeknologi,
                            terpilih = vm.teknologiForm,
                            siap = vm.formSiap,
                            onJudul = { vm.judulForm = it },
                            onDeskripsi = { vm.deskripsiForm = it },
                            onPilih = { tek -> vm.pilih(tek) },
                            onSimpan = { vm.simpan(k.id) },
                            onBatal = { vm.tutupDialog() }
                        )
                    }
                }
            }

            composable<ProjectDetailRoute> { entri ->
                val rute = entri.toRoute<ProjectDetailRoute>()
                val p = vm.proyek.find { it.id == rute.projectId }

                if (p != null) {
                    val namaKomunitas = vm.komunitas.find { it.id == p.idKomunitas }?.nama ?: ""
                    LayarDetail(
                        proyek = p,
                        namaKomunitas = namaKomunitas,
                        onKembali = { nav.popBackStack() },
                        onStatus = { vm.ubahStatus(p.id) }
                    )
                }
            }
        }
    }
}