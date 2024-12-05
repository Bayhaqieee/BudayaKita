package com.example.budayakita.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.budayakita.databinding.OnboardingItemBinding

class OnboardingAdapter(
    private val context: Context,
    private var onboardingItems: List<OnboardingItem>
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = OnboardingItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = onboardingItems[position]

        holder.binding.apply {
            // Gunakan Glide untuk memuat gambar secara efisien
            Glide.with(context)
                .load(item.imageRes)
                .into(onboardingImage)
            onboardingTitle.text = context.getString(item.titleRes)
            onboardingDescription.text = context.getString(item.descriptionRes)
        }
    }

    override fun getItemCount(): Int = onboardingItems.size

    inner class OnboardingViewHolder(val binding: OnboardingItemBinding) : RecyclerView.ViewHolder(binding.root)
}
