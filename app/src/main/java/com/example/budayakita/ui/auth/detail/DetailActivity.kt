package com.example.budayakita.ui.auth.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.budayakita.databinding.ActivityDetailBinding
import com.example.budayakita.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fileName = intent.getStringExtra("file_name") ?: return

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        viewModel.budaya.observe(this) { budaya ->
            binding.title.text = budaya.label
            binding.description.text = budaya.description
            Glide.with(this).load(budaya.image_url).into(binding.image)
        }

        viewModel.fetchBudayaDetails(fileName)
    }
}
