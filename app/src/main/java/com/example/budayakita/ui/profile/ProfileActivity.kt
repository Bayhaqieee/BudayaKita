package com.example.budayakita.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.budayakita.MainActivity
import com.example.budayakita.R
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.UserPreference
import com.example.budayakita.data.model.dataStore
import com.example.budayakita.data.network.ApiClient
import com.example.budayakita.databinding.ActivityProfileBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.login.LoginActivity
import com.example.budayakita.ui.explore.ExploreActivity
import com.example.budayakita.ui.glossary.GlossaryActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userRepository = UserRepository.getInstance(
            ApiClient.instance, UserPreference.getInstance(this.dataStore)
        )
        profileViewModel = ViewModelProvider(this, ViewModelFactory(userRepository)).get(ProfileViewModel::class.java)


        setupBottomNavigation()


        profileViewModel.userSession.observe(this) { user ->
            if (user.isLogin) {

            } else {

            }
        }


        profileViewModel.isDarkModeEnabled.observe(this) { isEnabled ->
            binding.switchDarkMode.isChecked = isEnabled
            updateAppTheme(isEnabled)
        }

        profileViewModel.getSession()
        profileViewModel.loadDarkModePreference()

        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            navigateToLogin()
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            profileViewModel.toggleDarkMode(isChecked)
        }
    }

    private fun updateAppTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupBottomNavigation() {
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
            CurvedBottomNavigation.Model(4, "Profile", R.drawable.ic_profile)
        )
        bottomNavigation.show(4, true)

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> startActivity(Intent(this, MainActivity::class.java))
                2 -> startActivity(Intent(this, GlossaryActivity::class.java))
                3 -> startActivity(Intent(this, ExploreActivity::class.java))
                4 -> { }
            }
        }
    }
}