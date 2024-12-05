package com.example.budayakita.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.budayakita.MainActivity
import com.example.budayakita.databinding.ActivityLoginBinding
import com.example.budayakita.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSession()
        setupAction()
    }

    private fun checkSession() {
        viewModel.getSession().observe(this) { userModel ->
            if (userModel.isLogin && userModel.token.isNotEmpty()) {
                navigateToMain()
            } else {

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
                    if (!response.error) {
                        response.loginResult.token.let {
                            viewModel.saveSession(it)
                        }

                        navigateToMain()
                    } else {
                        showError(response.message)
                    }
                }
                result.onFailure {
                    showError("Login failed: ${it.message}")
                }
            }
        }
    }


    private fun validateForm(): Boolean {
        return when {
            binding.edLoginEmail.error != null -> {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                false
            }

            binding.edLoginPassword.error != null -> {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            binding.edLoginEmail.text.isNullOrEmpty() -> {
                binding.edLoginEmail.error = "Email cannot be empty"
                false
            }

            binding.edLoginPassword.text.isNullOrEmpty() -> {
                binding.edLoginPassword.error = "Password cannot be empty"
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