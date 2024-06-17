package com.capstone.hay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hay.R
import com.capstone.hay.data.model.CarouselItem

class CarouselAdapter(private val items: List<CarouselItem>) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        holder.imageViewBg.setImageResource(item.imageBackground)
        holder.imageViewObj.setImageResource(item.imageObject)
        holder.titleTextView.text = item.title
    }

    override fun getItemCount(): Int = items.size

    inner class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewBg: ImageView = view.findViewById(R.id.imgBg)
        val imageViewObj: ImageView = view.findViewById(R.id.imgObject)
        val titleTextView: TextView = view.findViewById(R.id.titleCarousel)
    }
}
