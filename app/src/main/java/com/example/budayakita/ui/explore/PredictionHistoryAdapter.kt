package com.example.budayakita.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.budayakita.data.response.PredictionHistoryItem
import com.example.budayakita.databinding.ItemPredictionHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class PredictionHistoryAdapter :
    ListAdapter<PredictionHistoryItem, PredictionHistoryAdapter.PredictionHistoryViewHolder>(
        PredictionHistoryDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionHistoryViewHolder {
        val binding = ItemPredictionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PredictionHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PredictionHistoryViewHolder(
        private val binding: ItemPredictionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PredictionHistoryItem) {
            Glide.with(binding.root.context)
                .load(item.file_url)
                .into(binding.historyImage)

            binding.historyLabel.text = item.label_name
            binding.historyDate.text = formatDate(item.created_at)
        }

        private fun formatDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            return try {
                val date = inputFormat.parse(dateString)
                date?.let { outputFormat.format(it) } ?: dateString
            } catch (e: Exception) {
                dateString
            }
        }
    }

    class PredictionHistoryDiffCallback : DiffUtil.ItemCallback<PredictionHistoryItem>() {
        override fun areItemsTheSame(
            oldItem: PredictionHistoryItem,
            newItem: PredictionHistoryItem
        ): Boolean = oldItem.filename == newItem.filename

        override fun areContentsTheSame(
            oldItem: PredictionHistoryItem,
            newItem: PredictionHistoryItem
        ): Boolean = oldItem == newItem
    }
}