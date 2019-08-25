package com.example.androidtest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R
import com.example.androidtest.ui.OnLocationTappedListener

class LocationListAdapter(val listener: OnLocationTappedListener) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtLocation: TextView = itemView.findViewById(R.id.tv_location)
    }

    private val locationList = mutableListOf<String>()

    override fun getItemCount(): Int = locationList.size

    fun setLocationData(list: List<String>?) {
        list?.let {
            locationList.clear()
            locationList.addAll(list)
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtLocation.text = locationList[position]
        holder.itemView.setOnClickListener { view -> listener.onItemTapped(locationList[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
        return ViewHolder(v)
    }
}