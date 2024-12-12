package com.example.budayakita.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.budayakita.databinding.FragmentSuccessExploreBinding

class SuccessExploreFragment : Fragment() {

    private lateinit var binding: FragmentSuccessExploreBinding

    companion object {
        private const val ARG_IMAGE_URL = "image_url"
        private const val ARG_PREDICTION = "prediction"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(imageUrl: String, prediction: String, description: String): SuccessExploreFragment {
            val fragment = SuccessExploreFragment()
            val args = Bundle().apply {
                putString(ARG_IMAGE_URL, imageUrl)
                putString(ARG_PREDICTION, prediction)
                putString(ARG_DESCRIPTION, description)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = arguments?.getString(ARG_IMAGE_URL)
        val prediction = arguments?.getString(ARG_PREDICTION)
        val description = arguments?.getString(ARG_DESCRIPTION)

        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.resultImage)

        binding.titleResult.text = prediction
        binding.description.text = description

        binding.btnDetail.setOnClickListener {
            // TODO: Implement detail view or navigation
        }

        binding.btnReUpload.setOnClickListener {
            // Navigate back to upload screen or reset the upload process
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}