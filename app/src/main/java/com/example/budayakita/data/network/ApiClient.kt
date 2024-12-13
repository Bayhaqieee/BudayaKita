package com.example.budayakita.data.network

import com.example.budayakita.BuildConfig
import com.example.budayakita.data.model.UserPreference
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object ApiClient {

    private const val BASE_URL = BuildConfig.BASE_URL

    private fun createClient(token: String? = null): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        httpClient.addInterceptor(logging)

        httpClient.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            requestBuilder.addHeader("Content-Type", "application/json")
            chain.proceed(requestBuilder.build())
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

    // Fungsi untuk membuat Multipart untuk file upload
    fun prepareFilePart(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull()) // Menyesuaikan dengan tipe file
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    // Fungsi untuk membuat RequestBody dari userId
    fun prepareUserIdPart(userId: String): RequestBody {
        return userId.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}
