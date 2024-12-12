package com.example.budayakita.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.budayakita.MainActivity
import com.example.budayakita.databinding.ActivityLoginBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginToRegist.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        checkSession()
        setupAction()
    }

    private fun checkSession() {
        viewModel.getSession().observe(this) { userModel ->
            if (userModel.isLogin && userModel.token.isNotEmpty()) {
                Log.d("LoginActivity", "User is already logged in.")
                navigateToMain()
            }
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (!validateForm()) return@setOnClickListener

            binding.progressBar.visibility = View.VISIBLE

            viewModel.login(email, password).observe(this) { result ->
                binding.progressBar.visibility = View.GONE
                result.onSuccess { response ->
                    response.token.let { token ->
                        viewModel.saveSession(token)
                        Log.d("LoginActivity", "Token saved successfully.")
                        navigateToMain()
                    }
                }
                result.onFailure {
                    showError("Login failed: ${it.message}")
                    Log.e("LoginActivity", "Login failed", it)
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()

        return when {
            email.isEmpty() -> {
                binding.edLoginEmail.error = "Email cannot be empty"
                false
            }
            password.isEmpty() -> {
                binding.edLoginPassword.error = "Password cannot be empty"
                false
            }
            password.length < 8 -> {
                binding.edLoginPassword.error = "Password must be at least 8 characters"
                false
            }
            else -> true
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
