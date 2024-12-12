package com.example.budayakita.ui.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.budayakita.data.UserRepository
import kotlinx.coroutines.Dispatchers

class OtpViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun verifyOtp(email: String, otp: String) = liveData(Dispatchers.IO) {
        try {
            val response = userRepository.verifyOtp(email, otp)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
