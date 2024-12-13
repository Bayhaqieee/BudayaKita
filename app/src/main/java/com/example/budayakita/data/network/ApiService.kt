package com.example.budayakita.data.network

import com.example.budayakita.data.model.Budaya
import com.example.budayakita.data.response.BudayaResponse
import com.example.budayakita.data.response.LoginResponse
import com.example.budayakita.data.response.PredictionHistoryResponse
import com.example.budayakita.data.response.PredictionResponse
import com.example.budayakita.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @POST("send-otp")
    suspend fun sendOtp(
        @Body request: SendOtpRequest
    ): RegisterResponse

    @POST("verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @Multipart
    @POST("predict-image")
    suspend fun predictImage(
        @Part file: MultipartBody.Part,
        @Part("user_id") userId: RequestBody
    ): PredictionResponse


    @GET("predictionHistory")
    suspend fun getPredictionHistory(
        @Query("user_id") userId: String
    ): PredictionHistoryResponse

    @GET("getall_budaya")
    suspend fun getAllBudaya(): BudayaResponse

    @GET("details")
    suspend fun getBudayaDetails(
        @Query("file_name") fileName: String
    ): Budaya
}

data class SendOtpRequest(
    val email: String,
    val full_name: String,
    val password: String
)

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)

data class LoginRequest(
    val email: String,
    val password: String
)
