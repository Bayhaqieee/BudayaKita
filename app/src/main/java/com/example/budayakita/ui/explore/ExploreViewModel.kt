package com.example.budayakita.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.response.PredictionHistoryResponse
import com.example.budayakita.data.response.PredictionResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ExploreViewModel(private val repository: UserRepository) : ViewModel() {

    private val _predictionResponse = MutableLiveData<PredictionResponse>()
    val predictionResponse: LiveData<PredictionResponse> get() = _predictionResponse

    private val _predictionHistory = MutableLiveData<PredictionHistoryResponse>()
    val predictionHistory: LiveData<PredictionHistoryResponse> get() = _predictionHistory

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun predictImage(file: MultipartBody.Part, userId: RequestBody) {
        _loading.value = true
        viewModelScope.launch {
            try {
                // Memanggil API untuk prediksi gambar
                val response = repository.predictImage(file.toString(), userId.toString())
                _predictionResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }


    fun getPredictionHistory(userId: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getPredictionHistory(userId)
                _predictionHistory.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
