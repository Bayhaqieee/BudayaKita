package com.example.budayakita.ui.glossary


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.Budaya
import kotlinx.coroutines.launch

class GlossaryViewModel(private val repository: UserRepository) : ViewModel() {

    private val _budayaList = MutableLiveData<List<Budaya>>()
    val budayaList: LiveData<List<Budaya>> get() = _budayaList

    fun fetchAllBudaya() {
        viewModelScope.launch {
            try {
                val budaya = repository.getAllBudaya()
                _budayaList.postValue(budaya)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlossaryViewModel::class.java)) {
                return GlossaryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
