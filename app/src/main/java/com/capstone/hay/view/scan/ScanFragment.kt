package com.capstone.hay.view.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.capstone.hay.databinding.FragmentScanBinding
import com.google.android.material.appbar.MaterialToolbar

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardCheckYourHairCondition.setOnClickListener {
            val action = ScanFragmentDirections.actionScanFragmentToScanImageFragment()
            findNavController().navigate(action)
        }

        setupActionBack()
    }

    private fun setupActionBack() {
        val topAppBar: MaterialToolbar = binding.topAppBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}