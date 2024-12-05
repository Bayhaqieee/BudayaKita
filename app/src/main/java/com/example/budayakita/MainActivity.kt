package com.example.budayakita

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.budayakita.databinding.ActivityMainBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.register.RegisterActivity
import com.example.budayakita.ui.create.CreateActivity
import com.example.budayakita.ui.explore.ExploreActivity
import com.example.budayakita.ui.glossary.GlossaryActivity
import com.example.budayakita.ui.home.MainViewModel
import com.example.budayakita.ui.profile.ProfileActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = binding.bottomNavigation
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home", R.drawable.ic_home)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Glossary", R.drawable.ic_glossary)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Explore", R.drawable.ic_explore)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(4, "Create", R.drawable.ic_create)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(5, "Profile", R.drawable.ic_profile)
        )

        bottomNavigation.show(1, true)

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> { }
                2 -> startActivity(Intent(this, GlossaryActivity::class.java))
                3 -> startActivity(Intent(this, ExploreActivity::class.java))
                4 -> startActivity(Intent(this, CreateActivity::class.java))
                5 -> startActivity(Intent(this, ProfileActivity::class.java))
            }
        }


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

