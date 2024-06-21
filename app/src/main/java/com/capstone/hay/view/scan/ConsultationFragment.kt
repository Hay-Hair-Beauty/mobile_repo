package com.capstone.hay.view.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hay.R
import com.capstone.hay.adapter.ConsultationAdapter
import com.capstone.hay.data.model.ConsultModel
import com.capstone.hay.databinding.FragmentConsultationBinding
import com.google.android.material.appbar.MaterialToolbar

class ConsultationFragment : Fragment() {
    private var _binding: FragmentConsultationBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvConsult: RecyclerView
    private val viewModel: ConsultationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsultationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvConsult = binding.rvConsult
        rvConsult.setHasFixedSize(true)
        rvConsult.layoutManager = LinearLayoutManager(requireContext())

        setupObserve()
        setupActionBack()
    }

    private fun setupObserve() {
        viewModel.consultData.observe(viewLifecycleOwner) { consultData ->
            if (consultData != null) {
                showRecyclerList(consultData)
            }
        }

        if (viewModel.consultData.value.isNullOrEmpty()) {
            val list = getListConsult()
            viewModel.loadConsultData(list)
        }
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

    private fun getListConsult(): ArrayList<ConsultModel> {
        val dataName = resources.getStringArray(R.array.name_consult)
        val dataPlace = resources.getStringArray(R.array.place_consult)
        val dataYear = resources.getIntArray(R.array.year_consult)
        val dataSpecialist = resources.getStringArray(R.array.specialist_consult)
        val dataAbout = resources.getStringArray(R.array.about_consult)
        val dataSchedule = resources.getStringArray(R.array.schedule_consult)
        val dataContact = resources.getStringArray(R.array.contact_consult)
        val dataPhoto = resources.obtainTypedArray(R.array.photo_consult)

        val listConsult = ArrayList<ConsultModel>()
        for (i in dataName.indices) {
            val consult = ConsultModel(
                dataName[i], dataPlace[i], dataYear[i], dataSpecialist[i], dataAbout[i],
                dataSchedule[i], dataContact[i], dataPhoto.getResourceId(i, -1)
            )
            listConsult.add(consult)
        }
        dataPhoto.recycle()
        return listConsult
    }

    private fun showRecyclerList(consultList: ArrayList<ConsultModel>) {
        val listConsultAdapter = ConsultationAdapter(consultList)
        rvConsult.adapter = listConsultAdapter

        listConsultAdapter.setOnItemClickCallback(object : ConsultationAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ConsultModel) {
                showSelectedConsult(data)
            }
        })
    }

    private fun showSelectedConsult(consult: ConsultModel) {
        val action = ConsultationFragmentDirections.actionConsultationFragmentToDetailConsultationFragment(consult)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

