package com.capstone.hay.view.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hay.adapter.NewsAdapter
import com.capstone.hay.data.response.DataItem
import com.capstone.hay.databinding.FragmentNewsBinding
import com.capstone.hay.utils.CustomMarginItemDecoration
import com.capstone.hay.view.ViewModelFactory
import androidx.navigation.fragment.findNavController
import com.capstone.hay.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NewsViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.layoutManager = layoutManager
        val itemDecoration = CustomMarginItemDecoration()
        binding.rvNews.addItemDecoration(itemDecoration)

        viewModel.getAllArticle()
        setupObserve()
        setupActionBack()
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getAllArticleByTitle(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })
    }


    private fun setupObserve() {
        viewModel.articleResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                setListData(it.data)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isShowTextNotFound.observe(viewLifecycleOwner) {
            showTextNotFound(it)
        }

        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showSnackbar(message)

            }
        }
    }

    private fun showTextNotFound(isShowText: Boolean) {
        binding.textNotFound.visibility = if (isShowText) View.VISIBLE else View.GONE
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

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.newsFragment, message, Snackbar.LENGTH_LONG)
        snackbar.anchorView = requireActivity().findViewById(R.id.nav_view)
        snackbar.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.searchBar.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun setListData(listNews: List<DataItem>) {
        val adapter = NewsAdapter(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(news: DataItem) {
                showSelectedUser(news)
            }
        })
        adapter.submitList(listNews)
        binding.rvNews.adapter = adapter
    }

    private fun showSelectedUser(news: DataItem) {
        val action = NewsFragmentDirections.actionNewsFragmentToDetailNewsFragment(news.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}