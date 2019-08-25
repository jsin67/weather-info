package com.example.androidtest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R
import com.example.androidtest.models.WeatherForcastInformationUIModel
import com.example.androidtest.ui.OnWeatherTappedListener
import com.example.androidtest.utils.loadImage

class WeatherListAdapter(listener: OnWeatherTappedListener) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, tempFormat: String) : RecyclerView.ViewHolder(itemView) {
        val txtDate: TextView = itemView.findViewById(R.id.tv_date)
        val txtDescription: TextView = itemView.findViewById(R.id.tv_description)
        val txtTemp: TextView = itemView.findViewById(R.id.tv_temp)
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        val format = tempFormat;
    }

    private val weatherInfoList = mutableListOf<WeatherForcastInformationUIModel>()

    override fun getItemCount(): Int = weatherInfoList.size

    fun setWeatherData(list: List<WeatherForcastInformationUIModel>?) {
        list?.let {
            weatherInfoList.clear()
            weatherInfoList.addAll(list)
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uiModelForcast: WeatherForcastInformationUIModel = weatherInfoList[position]
        holder.txtDate.text = uiModelForcast.date
        holder.txtDescription.text = uiModelForcast.weatherDescription
        holder.txtTemp.text = String.format(holder.format, uiModelForcast.minTemp, uiModelForcast.maxTemp)
        holder.loadImage(holder.ivIcon, uiModelForcast.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v, parent.context.getString(R.string.min_max_temp))
    }
}