package com.capstone.hay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hay.data.response.DataItemResponse
import com.capstone.hay.databinding.ItemRecomendationBinding

class RecomendationProductAdapter : ListAdapter<DataItemResponse, RecomendationProductAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class MyViewHolder(
        val binding: ItemRecomendationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: DataItemResponse) {
            binding.productName.text = product.productName
            binding.descProduct.text = product.description

            Glide.with(binding.root.context)
                .load(product.productImage)
                .into(binding.productImage)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemResponse>() {
            override fun areItemsTheSame(
                oldItem: DataItemResponse,
                newItem: DataItemResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemResponse,
                newItem: DataItemResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}