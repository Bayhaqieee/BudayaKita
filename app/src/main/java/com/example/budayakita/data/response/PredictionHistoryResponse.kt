package com.example.budayakita.data.response

data class PredictionHistoryResponse(
    val history: List<PredictionHistoryItem>
)

data class PredictionHistoryItem(
    val created_at: String,
    val deskripsi: String,
    val file_url: String,
    val filename: String,
    val label_name: String,
    val user_id: String
)
