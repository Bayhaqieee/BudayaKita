package com.example.budayakita.data.network

import com.example.budayakita.data.response.LoginResponse
import com.example.budayakita.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

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
