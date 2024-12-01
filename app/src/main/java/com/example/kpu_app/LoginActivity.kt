package com.example.kpu_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kpu_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        if (sharedPreferencesHelper.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            // Lakukan validasi login
            sharedPreferencesHelper.saveLoginStatus(true)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}