package com.example.budayakita.ui.glossary


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.budayakita.R
import com.example.budayakita.data.model.Budaya
import com.example.budayakita.databinding.RvBatikItemBinding


class GlossaryAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<Budaya, GlossaryAdapter.BudayaViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudayaViewHolder {
        val binding = RvBatikItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudayaViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BudayaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BudayaViewHolder(
        private val binding: RvBatikItemBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(budaya: Budaya) {
            binding.title.text = budaya.label
            binding.description.text = budaya.description


            val imageUrl = budaya.image_url.replace("gs://bucket-budayakita", "https://storage.googleapis.com/bucket-budayakita")

            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.foto1)
                .error(R.drawable.foto1)
                .into(binding.image)

            binding.root.setOnClickListener {
                onItemClick(budaya.label)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Budaya>() {
        override fun areItemsTheSame(oldItem: Budaya, newItem: Budaya): Boolean {
            return oldItem.label == newItem.label
        }

        override fun areContentsTheSame(oldItem: Budaya, newItem: Budaya): Boolean {
            return oldItem == newItem
        }
    }
}
