package com.capstone.hay.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.hay.R
import com.capstone.hay.databinding.FragmentChangePasswordBinding
import com.google.android.material.appbar.MaterialToolbar

class ChangePasswordFragment : Fragment() {
    private var _binding :FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBack()
    }

    private fun setupActionBack() {
        val topAppBar: MaterialToolbar = binding.topAppBar
        topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)

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