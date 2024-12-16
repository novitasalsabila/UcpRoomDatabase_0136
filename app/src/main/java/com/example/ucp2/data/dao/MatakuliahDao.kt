package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MatakuliahDao {
    // Fungsi untuk mendapatkan semua data matakuliah
    @Query("SELECT * FROM matakuliah")
    fun getAllMatakuliah(): Flow<List<Matakuliah>>

    // Fungsi untuk memasukkan data matakuliah baru
    @Insert
    suspend fun insertMatakuliah(matakuliah: Matakuliah)

    // Fungsi untuk mendapatkan data matakuliah berdasarkan kode
    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMatakuliah(kode: String): Flow<Matakuliah>

    // Fungsi untuk menghapus data matakuliah
    @Delete
    suspend fun deleteMatakuliah(matakuliah: Matakuliah)

    // Fungsi untuk memperbarui data matakuliah
    @Update
    suspend fun updateMatakuliah(matakuliah: Matakuliah)
}
