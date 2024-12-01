package com.example.kpu_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PemilihDao {
    @Insert
    suspend fun insert(pemilih: Pemilih)

    @Update
    suspend fun update(pemilih: Pemilih)

    @Delete
    suspend fun delete(pemilih: Pemilih)

    @Query("SELECT * FROM pemilih_table ORDER BY id ASC")
    suspend fun getAllPemilih(): List<Pemilih>

    @Query("SELECT * FROM pemilih_table WHERE id = :pemilihId LIMIT 1")
    suspend fun getPemilihById(pemilihId: Int): Pemilih?

}