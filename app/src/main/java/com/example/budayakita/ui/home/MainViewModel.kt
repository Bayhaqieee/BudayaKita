package com.example.budayakita.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.Budaya
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

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
}
