package com.example.budayakita.data.network

import com.example.budayakita.data.model.ImagePredictionRequest
import com.example.budayakita.data.response.LoginResponse
import com.example.budayakita.data.response.PredictionHistoryResponse
import com.example.budayakita.data.response.PredictionResponse
import com.example.budayakita.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("predict-image")
    suspend fun predictImage(@Body request: ImagePredictionRequest): PredictionResponse

    @GET("prediction-history")
    suspend fun getPredictionHistory(@Query("user_id") userId: String): PredictionHistoryResponse
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
