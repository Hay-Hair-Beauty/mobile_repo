package com.capstone.hay.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.hay.R
import com.capstone.hay.databinding.FragmentPasswordSecurityBinding
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.login.LoginActivity
import com.google.android.material.appbar.MaterialToolbar

class PasswordSecurityFragment : Fragment() {
    private var _binding: FragmentPasswordSecurityBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordSecurityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                binding.emailInfoAccount.text = user.email
            }
        }

        binding.cardPasswordAndSecurity.setOnClickListener {
            val action = PasswordSecurityFragmentDirections.actionNavigationPasswordSecurityToNavigationChangePassword()
            findNavController().navigate(action)
        }
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