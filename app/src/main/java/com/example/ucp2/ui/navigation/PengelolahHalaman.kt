package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dosen.DestinasiInsertDosen
import com.example.ucp2.ui.view.dosen.DetailDosenView
import com.example.ucp2.ui.view.dosen.HomeDosenView
import com.example.ucp2.ui.view.dosen.HomeMenuView
import com.example.ucp2.ui.view.dosen.InsertDosenView
import com.example.ucp2.ui.view.matakuliah.DestinasiInsertMatkul
import com.example.ucp2.ui.view.matakuliah.HomeMatakuliahView
import com.example.ucp2.ui.view.matakuliah.InsertMatkulView
import com.example.ucp2.ui.view.matakuliah.UpdateMataKuliahView
import com.example.ucp2.ui.viewmodel.matakuliah.DetailMatakuliahView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeMenu.route
    ) {
        composable(
            route = DestinasiHomeMenu.route
        ) {
            HomeMenuView(
                onDosenClick = {
                    navController.navigate(DestinasiHomeDosen.route)
                },
                onMatkulClick = {
                    navController.navigate(DestinasiHomeMatakuliah.route)
                }
            )
        }
        // Layar Home Dosen
        composable(
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDetailDosen.route}/$nidn")
                    println("PengelolaHalaman: nidn = $nidn")
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }

        // Layar Home Matakuliah (Perbaikan)
        composable(
            route = DestinasiHomeMatakuliah.route
        ) {
            HomeMatakuliahView(  // Menggunakan HomeMatakuliahView
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMatakuliah.route}/$kode")
                },
                onAddMatkul = {  // Perubahan di sini
                    navController.navigate(DestinasiInsertMatkul.route)
                },
                modifier = modifier
            )
        }

        // Layar Insert Dosen
        composable (
            route = DestinasiInsertDosen.route
        ) {
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        // Layar Detail Dosen
        composable (
            DestinasiDetailDosen.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailDosen.NIDN) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailDosen.NIDN)

            kode?.let { kode ->
                DetailDosenView(
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }
        }

        // Detail Matkul
        composable (
            DestinasiDetailMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatakuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailMatakuliah.KODE)

            kode?.let { kode ->
                DetailMatakuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMatakuliah.route}/$kode")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // Layar Insert Matakuliah
        composable (
            route = DestinasiInsertMatkul.route
        ) {
            InsertMatkulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        // Layar Update Matakuliah
        composable (
            DestinasiUpdateMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatakuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailMatakuliah.KODE)

            kode?.let { kode ->
                DetailMatakuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMatakuliah.route}/$kode")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument (DestinasiUpdateMatakuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMataKuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}