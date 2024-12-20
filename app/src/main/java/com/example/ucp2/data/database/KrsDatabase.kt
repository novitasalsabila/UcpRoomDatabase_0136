package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen


@Database(entities = [Dosen::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {

    // Mendefinisikan fungsi untuk mengakses data Dosen, dan MataKuliah
    abstract fun DosenDao(): DosenDao

    companion object {
        @Volatile // Memastikan bahwa nilai variabel Instance selalu sama di semua thread
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    KrsDatabase::class.java, // Class database
                    "KrsDatabase" // Nama database
                )
                    .build().also { Instance = it }
            }
        }
    }
}