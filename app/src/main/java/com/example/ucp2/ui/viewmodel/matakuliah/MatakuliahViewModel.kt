package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import kotlinx.coroutines.launch

class MatakuliahViewModel(private val repositoryMatakuliah: RepositoryMatakuliah) : ViewModel() {

    var uiState by mutableStateOf(MatkulUIState())

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenisMatkul = if (event.jenisMatkul.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.matakuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatakuliah.insertMatakuliah(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        matakuliahEvent = MatakuliahEvent(), // reset input form
                        isEntryValid = FormErrorState() // reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Data tidak valid. Periksa kembali data anda"
            )
        }
    }

    // Reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(
            snackBarMessage = null
        )
    }
}

data class MatkulUIState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenisMatkul: String? = null,
    val dosenPengampu: String? = null
) {
    fun isValid(): Boolean {
        return kode == null && nama == null && sks == null && semester == null && jenisMatkul == null && dosenPengampu == null
    }
}

// Menyimpan input form ke dalam entity
fun MatakuliahEvent.toMatakuliahEntity(): Matakuliah = Matakuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenisMatkul = jenisMatkul,
    dosenPengampu = dosenPengampu
)

// Data class variabel yang menyimpan data input form
data class MatakuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenisMatkul: String = "",
    val dosenPengampu: String = ""
)
