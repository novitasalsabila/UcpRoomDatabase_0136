package com.example.ucp2.repository

import com.example.ucp2.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatakuliah {
        fun getAllMatakuliah(): Flow<List<Matakuliah>>
        fun getMatakuliah(kode: String): Flow<Matakuliah>
        suspend fun deleteMatakuliah(matakuliah: Matakuliah)
        suspend fun updateMatakuliah(matakuliah: Matakuliah)
        suspend fun insertMatakuliah(matakuliah: Matakuliah)
}