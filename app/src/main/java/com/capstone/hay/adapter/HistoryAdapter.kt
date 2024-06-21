package com.capstone.hay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hay.data.model.History
import com.capstone.hay.databinding.ItemHistoryBinding
import com.capstone.hay.utils.formatDate

class HistoryAdapter :
    PagedListAdapter<History, HistoryAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val history = getItem(position) as History
        holder.bind(history)
    }

    class WordViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: History) {
            binding.hairIssue.text = data.hairIssue
            binding.createdAt.text = formatDate(data.createdBy)
            binding.descriptionHairIssue.text = data.descHairIssue
            Glide.with(binding.root.context)
                .load(data.photo)
                .into(binding.imgHistory)
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }
    }
}