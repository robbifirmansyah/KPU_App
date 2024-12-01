package com.example.kpu_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kpu_app.database.PemilihDatabase
import com.example.kpu_app.databinding.ActivityShowBinding
import kotlinx.coroutines.launch

class ShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowBinding
    private lateinit var pemilihDatabase: PemilihDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pemilihDatabase = PemilihDatabase.getDatabase(this)

        // Ambil ID pemilih dari Intent
        val pemilihId = intent.getIntExtra("pemilih_id", -1)

        if (pemilihId != -1) {
            loadPemilihData(pemilihId)
        } else {
            // Tampilkan pesan kesalahan jika ID tidak valid
            binding.showNama.text = "Pemilih tidak ditemukan"
        }

        // Set up button kembali
        binding.btnKembali.setOnClickListener {
            finish() // Kembali ke activity sebelumnya
        }
    }

    private fun loadPemilihData(pemilihId: Int) {
        lifecycleScope.launch {
            val pemilih = pemilihDatabase.pemilihDao().getPemilihById(pemilihId)
            if (pemilih != null) {
                // Tampilkan data pemilih di UI
                binding.showNama.text = "Nama: ${pemilih.nama}"
                binding.showNik.text = "NIK: ${pemilih.nik}"
                binding.showGender.text = "Gender: ${pemilih.gender}"
                binding.showAlamat.text = "Alamat: ${pemilih.alamat}"
            } else {
                binding.showNama.text = "Pemilih tidak ditemukan"
            }
        }
    }
}