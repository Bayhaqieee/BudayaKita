package com.example.budayakita.ui.glossary

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budayakita.MainActivity
import com.example.budayakita.R
import com.example.budayakita.data.UserRepository
import com.example.budayakita.databinding.ActivityGlossaryBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.auth.detail.DetailActivity
import com.example.budayakita.ui.explore.ExploreActivity
import com.example.budayakita.ui.profile.ProfileActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class GlossaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGlossaryBinding
    private lateinit var viewModel: GlossaryViewModel
    private lateinit var adapter: GlossaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlossaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[GlossaryViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        setupBottomNavigation()
        viewModel.fetchAllBudaya()
    }

    private fun setupRecyclerView() {
        adapter = GlossaryAdapter { fileName ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("file_name", fileName)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.budayaList.observe(this) { budayaList ->
            adapter.submitList(budayaList)
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = binding.bottomNavigation
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home", R.drawable.ic_home)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Glossary", R.drawable.ic_glossary)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Explore", R.drawable.ic_explore)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(4, "Profile", R.drawable.ic_profile)
        )

        bottomNavigation.show(2, true)

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> startActivity(Intent(this, MainActivity::class.java))
                2 -> { } // Current activity, do nothing
                3 -> startActivity(Intent(this, ExploreActivity::class.java))
                4 -> startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
    }
}