package com.example.kpu_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kpu_app.database.Pemilih
import com.example.kpu_app.database.PemilihDatabase
import com.example.kpu_app.databinding.ActivityTambahBinding
import kotlinx.coroutines.launch

class tambah : AppCompatActivity() {
    private lateinit var binding: ActivityTambahBinding
    private lateinit var pemilihDatabase: PemilihDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pemilihDatabase = PemilihDatabase.getDatabase(this)

        binding.btnSimpanTambah.setOnClickListener {
            val nama = binding.inputNamaTambah.text.toString()
            val nik = binding.inputNikTambah.text.toString()
            val gender = if (binding.radioLakiTambah.isChecked) "Laki-laki" else "Perempuan"
            val alamat = binding.inputAlamatTambah.text.toString()

            val pemilih = Pemilih(nama = nama, nik = nik, gender = gender, alamat = alamat)

            lifecycleScope.launch {
                pemilihDatabase.pemilihDao().insert(pemilih)
                setResult(RESULT_OK) // Memberikan hasil sukses
                finish() // Kembali ke MainActivity
            }
        }

    }
}