package com.example.budayakita.data

import com.example.budayakita.data.model.UserModel
import com.example.budayakita.data.model.UserPreference
import com.example.budayakita.data.network.ApiService
import com.example.budayakita.data.response.LoginResponse
import com.example.budayakita.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        if (!response.error) {
            userPreference.saveToken(response.loginResult.token)
        }
        return response
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun saveToken(token: String) {
        userPreference.saveToken(token)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
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
