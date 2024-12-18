package com.example.budayakita.data

import com.example.budayakita.data.model.Budaya
import com.example.budayakita.data.model.UserModel
import com.example.budayakita.data.network.ApiService
import com.example.budayakita.data.network.ApiClient
import com.example.budayakita.data.model.UserPreference
import com.example.budayakita.data.network.LoginRequest
import com.example.budayakita.data.network.SendOtpRequest
import com.example.budayakita.data.network.VerifyOtpRequest
import com.example.budayakita.data.response.LoginResponse
import com.example.budayakita.data.response.PredictionHistoryResponse
import com.example.budayakita.data.response.PredictionResponse
import com.example.budayakita.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow


class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun sendOtp(email: String, fullName: String, password: String): RegisterResponse {
        val request = SendOtpRequest(email, fullName, password)
        return apiService.sendOtp(request)
    }

    suspend fun verifyOtp(email: String, otp: String): RegisterResponse {
        val request = VerifyOtpRequest(email, otp)
        return apiService.verifyOtp(request)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        val response = apiService.login(request)
        userPreference.saveToken(response.token)
        return response
    }

    suspend fun saveDarkModePreference(isDarkMode: Boolean) {
        userPreference.saveDarkModePreference(isDarkMode)
    }

    fun getDarkModePreference(): Flow<Boolean> {
        return userPreference.getDarkModePreference()
    }

    suspend fun saveToken(token: String) {
        userPreference.saveToken(token)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getAllBudaya(): List<Budaya> {
        return apiService.getAllBudaya().results
    }

    // Tambahkan fungsi untuk mendapatkan detail budaya
    suspend fun getBudayaDetails(fileName: String): Budaya {
        return apiService.getBudayaDetails(fileName)
    }


    suspend fun predictImage(filePath: String, userId: String): PredictionResponse {
        // Persiapkan MultipartBody.Part untuk file
        val filePart = ApiClient.prepareFilePart(filePath)
        // Persiapkan RequestBody untuk userId
        val userIdPart = ApiClient.prepareUserIdPart(userId)

        // Mengirimkan ke API dengan parameter file dan userId
        return apiService.predictImage(filePart, userIdPart)
    }

    suspend fun getPredictionHistory(userId: String): PredictionHistoryResponse {
        return apiService.getPredictionHistory(userId)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}
