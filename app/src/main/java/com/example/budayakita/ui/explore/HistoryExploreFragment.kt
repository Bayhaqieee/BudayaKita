package com.example.budayakita.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budayakita.databinding.FragmentHistoryExploreBinding
import com.example.budayakita.ui.ViewModelFactory

class HistoryExploreFragment : Fragment() {

    private lateinit var binding: FragmentHistoryExploreBinding
    private lateinit var historyAdapter: PredictionHistoryAdapter

    private val exploreViewModel: ExploreViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observePredictionHistory()
    }

    private fun setupRecyclerView() {
        historyAdapter = PredictionHistoryAdapter()
        binding.recyclerHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
    }

    private fun observePredictionHistory() {
        // TODO: Replace with actual user ID from session
        val currentUserId = ""
        exploreViewModel.getPredictionHistory(currentUserId)

        exploreViewModel.predictionHistory.observe(viewLifecycleOwner) { historyResponse ->
            historyAdapter.submitList(historyResponse.history)
        }
    }
}