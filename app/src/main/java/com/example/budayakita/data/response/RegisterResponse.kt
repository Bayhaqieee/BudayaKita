package com.example.budayakita.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("message")
    val message: String
)