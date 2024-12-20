package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    // Fungsi untuk mendapatkan semua data dosen
    @Query("SELECT * FROM dosen")
    fun getAllDosen(): Flow<List<Dosen>>

    // Fungsi untuk memasukkan data dosen baru
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    // Fungsi untuk mendapatkan data dosen berdasarkan NIDN
    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>

    // Fungsi untuk menghapus data dosen
//    @Delete
//    suspend fun deleteDosen(dosen: Dosen)
//
//    // Fungsi untuk memperbarui data dosen
//    @Update
//    suspend fun updateDosen(dosen: Dosen)
}
