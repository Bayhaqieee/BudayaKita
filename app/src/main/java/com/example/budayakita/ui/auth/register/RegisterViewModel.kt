package com.example.budayakita.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.budayakita.data.UserRepository
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = liveData(Dispatchers.IO) {
        try {
            val response = repository.register(name, email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
