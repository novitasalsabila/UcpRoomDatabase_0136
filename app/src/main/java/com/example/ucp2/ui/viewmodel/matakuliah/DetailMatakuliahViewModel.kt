package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import com.example.ucp2.ui.navigation.DestinasiDetailMatakuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {

    // Mendapatkan kode matakuliah dari SavedStateHandle
    private val kode: String = savedStateHandle[DestinasiDetailMatakuliah.KODE] ?: ""

    // State untuk UI, yang akan diupdate berdasarkan data dari repository
    val detailUiState: StateFlow<DetailUiState> = repositoryMatakuliah.getMatakuliah(kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailMatakuliahEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true)) // Emit state awal saat loading
            delay(600) // Simulasi loading
        }
        .catch { exception ->
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "Terjadi kesalahan."
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000), // Hanya aktif ketika UI berlangganan
            initialValue = DetailUiState(isLoading = true) // State awal saat belum ada data
        )

    // Fungsi untuk menghapus data matakuliah
    fun deleteMatakuliah() {
        val matakuliah = detailUiState.value.detailUiEvent.toMatakuliahEntity()
        viewModelScope.launch {
            repositoryMatakuliah.deleteMatakuliah(matakuliah)
        }
    }
}

// Data class untuk menyimpan state yang akan ditampilkan di UI
data class DetailUiState(
    val detailUiEvent: MatakuliahEvent = MatakuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    // Properti untuk mengecek apakah data kosong
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MatakuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MatakuliahEvent()
}


// Ekstensi fungsi untuk memetakan data dari entity ke event
fun Matakuliah.toDetailMatakuliahEvent(): MatakuliahEvent {
    return MatakuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenisMatkul = jenisMatkul,
        dosenPengampu = dosenPengampu
    )
}

