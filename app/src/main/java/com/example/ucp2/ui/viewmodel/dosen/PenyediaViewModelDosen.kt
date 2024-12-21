package com.example.ucp2.ui.viewmodel.dosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp
import com.example.ucp2.ui.viewmodel.matakuliah.DetailMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.HomeMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMataKuliahViewModel

object PenyediaViewModelDosen {

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        //matakuliah
            initializer {
                MatakuliahViewModel(
                    krsApp().containerApp.repositoryMataKuliah
                )
            }

            initializer {
                HomeMatakuliahViewModel(
                    krsApp().containerApp.repositoryMataKuliah
                )
            }

            initializer {
                DetailMatakuliahViewModel(
                    createSavedStateHandle(),
                    krsApp().containerApp.repositoryMataKuliah,
                )
            }

            initializer {
                UpdateMataKuliahViewModel(
                    createSavedStateHandle(),
                    krsApp().containerApp.repositoryMataKuliah,
                )
            }
        }
    }

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
