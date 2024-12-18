package com.example.budayakita.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.budayakita.databinding.ActivityRegisterBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.login.LoginActivity
import com.example.budayakita.ui.auth.otp.OtpActivity

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        setupAction()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (!validateForm()) return@setOnClickListener

            binding.progressBar.visibility = View.VISIBLE

            viewModel.register(name, email, password).observe(this) { result ->
                binding.progressBar.visibility = View.GONE
                result.onSuccess { response ->
                    showSuccessDialog(response.message)
                }
                result.onFailure {
                    showError("Registration failed: ${it.message}")
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        return when {
            binding.edRegisterName.text.isNullOrEmpty() -> {
                binding.edRegisterName.error = "Name cannot be empty"
                false
            }
            binding.edRegisterEmail.text.isNullOrEmpty() -> {
                binding.edRegisterEmail.error = "Email cannot be empty"
                false
            }
            binding.edRegisterPassword.text.isNullOrEmpty() -> {
                binding.edRegisterPassword.error = "Password cannot be empty"
                false
            }
            binding.edRegisterPassword.text.toString().length < 8 -> {
                binding.edRegisterPassword.error = "Password must be at least 8 characters"
                false
            }
            else -> true
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                val intent = Intent(this@RegisterActivity, OtpActivity::class.java)
                intent.putExtra("email", binding.edRegisterEmail.text.toString())
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }
}