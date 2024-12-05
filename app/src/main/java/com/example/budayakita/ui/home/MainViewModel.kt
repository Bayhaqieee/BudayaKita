package com.example.budayakita.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Fungsi untuk logout
    fun logout() {
        viewModelScope.launch {
            userRepository.logout() // Panggil logout dari repository untuk menghapus sesi
        }
    }

    // Fungsi untuk mendapatkan status sesi (apakah user sudah login)
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}
