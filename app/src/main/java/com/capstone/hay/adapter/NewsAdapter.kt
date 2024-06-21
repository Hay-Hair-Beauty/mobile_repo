package com.capstone.hay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hay.data.response.DataItem
import com.capstone.hay.databinding.ItemNewsBinding
import com.capstone.hay.utils.formatTimestamp

class NewsAdapter(private val onItemClickCallback: OnItemClickCallback) : ListAdapter<DataItem, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClickCallback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class MyViewHolder(
        val binding: ItemNewsBinding,
        private val onItemClickCallback: OnItemClickCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: DataItem) {
            val jsonTimestamp = mapOf("_seconds" to news.createdAt.seconds, "_nanoseconds" to news.createdAt.nanoseconds)
            val formattedDate = formatTimestamp(jsonTimestamp)
            binding.titleNews.text = news.title
            binding.descriptionNews.text = news.content
            binding.publishedNews.text = formattedDate

            Glide.with(binding.root.context)
                .load(news.image)
                .into(binding.imgNews)
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(news)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(news: DataItem)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}