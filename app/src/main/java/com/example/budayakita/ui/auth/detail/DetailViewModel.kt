package com.example.budayakita.ui.auth.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.Budaya
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UserRepository) : ViewModel() {

    private val _budaya = MutableLiveData<Budaya>()
    val budaya: LiveData<Budaya> get() = _budaya

    fun fetchBudayaDetails(fileName: String) {
        viewModelScope.launch {
            try {
                val details = repository.getBudayaDetails(fileName)
                _budaya.postValue(details)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
