package com.capstone.hay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hay.R
import com.capstone.hay.data.model.ConsultModel

class ConsultationAdapter(private val listConsult: ArrayList<ConsultModel>) : RecyclerView.Adapter<ConsultationAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consultation, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listConsult.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, place, year, specialist, about, schedule, contact, photo) = listConsult[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvPlace.text = place
        holder.tvYear.text = year.toString()
        holder.tvSpecialist.text = specialist
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listConsult[holder.adapterPosition])
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgProfile)
        val tvName: TextView = itemView.findViewById(R.id.nameProfile)
        val tvPlace: TextView = itemView.findViewById(R.id.placeProfile)
        val tvYear: TextView = itemView.findViewById(R.id.infoYearConsult)
        val tvSpecialist: TextView = itemView.findViewById(R.id.statusConsult)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ConsultModel)
    }
}