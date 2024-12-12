package com.example.budayakita.data.response


data class PredictionResponse(
    val confidence: Float,
    val created_at: String,
    val deskripsi: String,
    val file_url: String,
    val prediction: String,
    val user_id: String
)
