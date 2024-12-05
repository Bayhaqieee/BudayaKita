package com.example.budayakita.data.network

import com.example.budayakita.BuildConfig
import com.example.budayakita.data.model.UserPreference
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = BuildConfig.BASE_URL

    private fun createClient(token: String? = null): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (!token.isNullOrEmpty()) {
            httpClient.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
        }

        return httpClient.build()
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val instance: ApiService by lazy {
        val client = createClient()
        createRetrofit(client).create(ApiService::class.java)
    }

    suspend fun getAuthenticatedApiService(userPreference: UserPreference): ApiService {
        val token = userPreference.getSession().first().token
        val client = createClient(token)
        return createRetrofit(client).create(ApiService::class.java)
    }
}
