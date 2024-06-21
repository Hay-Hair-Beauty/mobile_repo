package com.capstone.hay.view.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.hay.R
import com.capstone.hay.databinding.FragmentProfileBinding
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.login.LoginActivity
import com.google.android.material.appbar.MaterialToolbar

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                binding.userName.text = resources.getString(R.string.present_user, user.name)
            }
        }

        binding.btnLogout.setBackgroundResource(R.drawable.logout_btn)
        setupActionButton()
        setupActionBack()
    }

    private fun setupActionButton() {
        binding.cardAboutUs.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAboutUsFragment()
            findNavController().navigate(action)
        }

        binding.cardProfile.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToInfoProfileFragment()
            findNavController().navigate(action)
        }

        binding.cardPasswordAndSecurity.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToNavigationPasswordSecurity()
            findNavController().navigate(action)
        }

        binding.cardLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
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