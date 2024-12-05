package com.example.budayakita


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.budayakita.databinding.ActivityMainBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.register.RegisterActivity
import com.example.budayakita.ui.home.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        observeSession()
    }

    private fun setupAction() {
        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Panggil fungsi logout dari MainViewModel
        viewModel.logout()

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish() // Hapus MainActivity dari stack agar tidak bisa kembali
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { userModel ->
            // Menampilkan nama pengguna atau melakukan pengecekan lain
            if (!userModel.isLogin) {
                navigateToRegister() // Jika tidak login, arahkan ke LoginActivity
            }
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish() // Hapus MainActivity dari stack agar tidak bisa kembali
    }
}

