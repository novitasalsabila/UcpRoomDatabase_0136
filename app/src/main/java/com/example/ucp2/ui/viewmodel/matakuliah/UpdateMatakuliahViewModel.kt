package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import com.example.ucp2.ui.navigation.DestinasiUpdateMatakuliah
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {
    var updateUiState by mutableStateOf(MatkulUIState())
        private set
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMatakuliah.KODE])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMatakuliah.getMatakuliah(_kode)
                .filterNotNull()
                .first()
                .toUiStateMatakuliah()
        }
    }

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        updateUiState = updateUiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode Mata Kuliah tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Mata Kuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS harus lebih dari 0",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenisMatkul  = if (event.jenisMatkul.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


    fun updateData() {
        val currentEvent = updateUiState.matakuliahEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatakuliah.updateMatakuliah(currentEvent.toMatakuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data tidak valid"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun Matakuliah.toUiStateMatakuliah(): MatkulUIState = MatkulUIState(
    matakuliahEvent = this.toDetailMatakuliahEvent()
)
