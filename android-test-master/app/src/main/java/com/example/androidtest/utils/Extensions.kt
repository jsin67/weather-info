package com.example.androidtest.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


fun RecyclerView.ViewHolder.loadImage(imageView: ImageView, url: String?) {
    val BASE_URL= "http://openweathermap.org/img/wn/"
    val placeholder = ColorDrawable(Color.WHITE)
    val requestOptions = RequestOptions
        .placeholderOf(placeholder)
        .fitCenter()
        .optionalCenterCrop()
    Log.d("jaggrat", BASE_URL+ url + ".png")
    Glide.with(itemView.context)
        .load(BASE_URL+ url + ".png")
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}