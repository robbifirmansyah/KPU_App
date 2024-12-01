package com.example.kpu_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kpu_app.database.Pemilih
import com.example.kpu_app.database.PemilihDatabase
import com.example.kpu_app.databinding.ActivityEditBinding
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var pemilihDatabase: PemilihDatabase
    private var pemilihId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pemilihDatabase = PemilihDatabase.getDatabase(this)

        // Ambil data pemilih dari intent
        pemilihId = intent.getIntExtra("pemilih_id", 0)

        loadPemilihData()

        binding.btnSimpanEdit.setOnClickListener {
            val nama = binding.inputNamaEdit.text.toString()
            val nik = binding.inputNikEdit.text.toString()
            val gender = if (binding.radioLakiEdit.isChecked) "Laki-laki" else "Perempuan"
            val alamat = binding.inputAlamatEdit.text.toString()

            val pemilih = Pemilih(id = pemilihId, nama = nama, nik = nik, gender = gender, alamat = alamat)

            lifecycleScope.launch {
                pemilihDatabase.pemilihDao().update(pemilih)
                finish() // Kembali ke MainActivity setelah mengupdate
            }
        }
    }

    private fun loadPemilihData() {
        lifecycleScope.launch {
            val pemilih = pemilihDatabase.pemilihDao().getAllPemilih().find { it.id == pemilihId }
            pemilih?.let {
                binding.inputNamaEdit.setText(it.nama)
                binding.inputNikEdit.setText(it.nik)
                if (it.gender == "Laki-laki") {
                    binding.radioLakiEdit.isChecked = true
                } else {
                    binding.radioPerempuanEdit.isChecked = true
                }
                binding.inputAlamatEdit.setText(it.alamat)
            }
        }
    }
}