package com.capstone.hay.view.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.hay.R
import com.capstone.hay.adapter.RecomendationProductAdapter
import com.capstone.hay.data.model.History
import com.capstone.hay.data.response.DataItemResponse
import com.capstone.hay.databinding.FragmentResultScanBinding
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.history.HistoryViewModel
import com.capstone.hay.view.home.HomeViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultScanFragment : Fragment() {
    private var _binding: FragmentResultScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ResultScanViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    private val viewModelHistory by viewModels<HistoryViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    private val viewModelAuth by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hairIssue = ResultScanFragmentArgs.fromBundle(requireArguments()).labelHairIssue
        val image = ResultScanFragmentArgs.fromBundle(requireArguments()).image
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvRecommendationProducts.layoutManager = layoutManager
        viewModel.getHairIssue(hairIssue)
        setupActionConsult()
        setupObserve(image)
    }

    private fun setupActionConsult() {
        binding.consultButton.setOnClickListener {
            val action = ResultScanFragmentDirections.actionResultScanFragmentToConsultationFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupObserve(image: String) {
        viewModel.hairIssuesResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                setData(it.data[0])
                setAdapter(it.data)
                saveHistory(it.data[0], image)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showSnackbar(message)
                binding.hairIssueDef.visibility = View.GONE
                binding.descHairIssueDef.visibility = View.GONE
                binding.causedBy.visibility = View.GONE
                binding.descCausedBy.visibility = View.GONE
                binding.treatment.visibility = View.GONE
                binding.descTreatment.visibility = View.GONE
                binding.recomendation.visibility = View.GONE
                binding.descRecomendation.visibility = View.GONE
                binding.rvRecommendationProducts.visibility = View.GONE
                binding.consultButton.visibility = View.GONE
            }
        }
        setupActionBack()
    }

    private fun saveHistory(dataItemResponse: DataItemResponse, image: String) {
        val currentTime = System.currentTimeMillis()
        viewModelAuth.getSession().observe(requireActivity()) {
            val newHistory = History(
                email = it.email,
                hairIssue = dataItemResponse.hairIssue,
                descHairIssue = dataItemResponse.hairIssueDef,
                causedBy = dataItemResponse.causedBy,
                treatment = dataItemResponse.treatment,
                photo = image,
                createdBy = currentTime
            )

            CoroutineScope(Dispatchers.IO).launch {
                viewModelHistory.insertData(newHistory)
            }
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

    private fun setAdapter(data: List<DataItemResponse>) {
        val adapter = RecomendationProductAdapter()
        adapter.submitList(data)
        binding.rvRecommendationProducts.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicatour.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.hairIssueDef.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.descHairIssueDef.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.causedBy.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.descCausedBy.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.treatment.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.descTreatment.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.recomendation.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.descRecomendation.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.rvRecommendationProducts.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.consultButton.visibility = if (isLoading) View.GONE else View.VISIBLE

    }

    private fun setData(hairIssue: DataItemResponse) {
        binding.apply {
            hairIssueDef.text = hairIssue.hairIssue
            descHairIssueDef.text = hairIssue.hairIssueDef
            descCausedBy.text = hairIssue.causedBy
            descTreatment.text = hairIssue.treatment
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.resultFragment, message, Snackbar.LENGTH_LONG)
        snackbar.anchorView = requireActivity().findViewById(R.id.nav_view)
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}