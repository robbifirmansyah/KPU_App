package com.example.kpu_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Pemilih::class], version = 1)
abstract class PemilihDatabase : RoomDatabase() {
    abstract fun pemilihDao(): PemilihDao

    companion object {
        @Volatile
        private var INSTANCE: PemilihDatabase? = null

        fun getDatabase(context: Context): PemilihDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,PemilihDatabase::class.java,"pemilih_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}