package com.example.budayakita.di

import android.content.Context
import com.example.budayakita.data.UserRepository
import com.example.budayakita.data.model.UserPreference
import com.example.budayakita.data.model.dataStore
import com.example.budayakita.data.network.ApiClient

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.instance
        return UserRepository.getInstance(apiService, userPreference)
    }

}