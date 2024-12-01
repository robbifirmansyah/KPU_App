package com.example.kpu_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kpu_app.database.Pemilih
import com.example.kpu_app.database.PemilihDatabase
import com.example.kpu_app.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pemilihDatabase: PemilihDatabase
    private lateinit var pemilihAdapter: PemilihAdapter
    private lateinit var tambahDataLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pemilihDatabase = PemilihDatabase.getDatabase(this)
        pemilihAdapter = PemilihAdapter(
            onEditClick = { pemilih -> editPemilih(pemilih) },
            onDeleteClick = { pemilih -> deletePemilih(pemilih) },
            onShowClick = { pemilih -> showPemilih(pemilih) }
        )

        binding.rcDataPemilih.layoutManager = LinearLayoutManager(this)
        binding.rcDataPemilih.adapter = pemilihAdapter

        binding.btnTambahData.setOnClickListener {
            val intent = Intent(this, tambah::class.java)
            tambahDataLauncher.launch(intent)
        }


        binding.btnLogout.setOnClickListener {
            SharedPreferencesHelper(this).logout()
            finish()
        }

        tambahDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Data baru ditambahkan, muat ulang data di RecyclerView
                loadPemilihData()
            }
        }


        loadPemilihData()
    }

    private fun loadPemilihData() {
        lifecycleScope.launch {
            val pemilihList = pemilihDatabase.pemilihDao().getAllPemilih()
            pemilihAdapter.submitList(pemilihList)
        }
    }

    private fun editPemilih(pemilih: Pemilih) {
        // Implementasi untuk mengedit pemilih
        val intent = Intent(this, EditActivity::class.java).apply {
            putExtra("pemilih_id", pemilih.id)
        }
        startActivity(intent)
    }

    private fun deletePemilih(pemilih: Pemilih) {
        // Implementasi untuk menghapus pemilih
        lifecycleScope.launch {
            pemilihDatabase.pemilihDao().delete(pemilih)
            loadPemilihData() // Refresh data setelah penghapusan
        }
    }

    private fun showPemilih(pemilih: Pemilih) {
        // Implementasi untuk menampilkan detail pemilih
        val intent = Intent(this, ShowActivity::class.java).apply {
            putExtra("pemilih_id", pemilih.id)
        }
        startActivity(intent)
    }
}