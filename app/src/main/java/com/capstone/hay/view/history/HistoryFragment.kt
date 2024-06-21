package com.capstone.hay.view.history

import android.os.Bundle
import android.view.*
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hay.R
import com.capstone.hay.adapter.HistoryAdapter
import com.capstone.hay.databinding.FragmentHistoryBinding
import com.capstone.hay.utils.formatDate
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.home.HomeViewModel
import com.capstone.hay.view.news.NewsViewModel
import com.google.android.material.datepicker.MaterialDatePicker

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HistoryViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    private val viewModelAuth by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.inflateMenu(R.menu.top_app_bar_history)
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.topAppBar.setOnMenuItemClickListener  {
            when (it.itemId) {
                R.id.calender_menu -> {
                    showCalender()
                    true
                }
                else -> false
            }
        }

        viewModelAuth.getSession().observe(requireActivity(), {
            getAllHistory(it.email)
            email = it.email
        })

    }

    private fun getAllHistory(email : String) {
        val adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter
        viewModel.getAllHistory(null, null, email).observe(requireActivity(), {
            adapter.submitList(it)
        })
    }

    private fun showCalender() {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select date range")
            .setTheme(R.style.ThemeCalender)
            .setSelection(Pair(null, null))
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val startDate = selection.first
            val endDate = selection.second
            if (startDate != null && endDate != null) {
                email?.let {
                    viewModel.getAllHistory(startDate, endDate, it).observe(requireActivity(), {
                        val adapter = HistoryAdapter()
                        binding.rvHistory.adapter = adapter
                        adapter.submitList(it)
                        binding.listTitleHistory.text = formatDate(startDate) + " - " + formatDate(endDate)

                    })
                }
            }
        }

        datePicker.show(parentFragmentManager, datePicker.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
