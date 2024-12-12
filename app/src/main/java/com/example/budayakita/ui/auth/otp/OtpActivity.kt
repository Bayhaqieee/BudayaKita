package com.example.budayakita.ui.auth.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.budayakita.databinding.ActivityOtpBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.login.LoginActivity

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private val viewModel: OtpViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.verifyButton.setOnClickListener {
            val email = binding.edOtpEmail.text.toString()
            val otp = binding.edOtpCode.text.toString()

            if (email.isEmpty() || otp.isEmpty()) {
                showError("Email and OTP cannot be empty")
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE

            viewModel.verifyOtp(email, otp).observe(this) { result ->
                binding.progressBar.visibility = View.GONE
                result.onSuccess { response ->
                    if (response.message == "Akun berhasil dibuat") {
                        showSuccessDialog()
                    } else {
                        showError(response.message)
                    }
                }
                result.onFailure {
                    showError("Verification failed: ${it.message}")
                }
            }
        }
    }

    private fun showSuccessDialog() {
        Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}