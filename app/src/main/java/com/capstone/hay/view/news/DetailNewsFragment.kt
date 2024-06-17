package com.capstone.hay.view.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.capstone.hay.R
import com.capstone.hay.data.response.DataItem
import com.capstone.hay.databinding.FragmentDetailNewsBinding
import com.capstone.hay.utils.formatTimestamp
import com.capstone.hay.view.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewsViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsId = DetailNewsFragmentArgs.fromBundle(requireArguments()).newsId

        Log.d("DetailNewsFragment", newsId)

        viewModel.articleByIdResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                setData(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showSnackbar(message)
            }
        }

        viewModel.getArticleById(newsId)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progresBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.thumbnailNews.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.titleNews.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.infoNewsLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.descNews.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.hayLogo.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun setData(news: DataItem) {
        binding.apply {
            val jsonTimestamp = mapOf("_seconds" to news.createdAt.seconds, "_nanoseconds" to news.createdAt.nanoseconds)
            val formattedDate = formatTimestamp(jsonTimestamp)
            titleNews.text = news.title
            publishedNews.text = formattedDate
            descNews.text = news.content
            Glide.with(binding.root.context)
                .load(news.image)
                .into(thumbnailNews)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}