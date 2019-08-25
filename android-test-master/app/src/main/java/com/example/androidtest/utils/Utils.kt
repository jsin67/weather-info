package com.example.androidtest.utils

import android.content.Context
import android.net.ConnectivityManager
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Checks internet connection
 */
fun verifyAvailableNetwork(activity: Context): Boolean {
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

/**
 * Converts to string date format
 * @param time: time value
 */
fun convertDateFormat(time: String): String {
    val sdfSource = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    val date = sdfSource.parse(time)

    val sdfDestination = SimpleDateFormat("E hh:mm a")

    return sdfDestination.format(date)
}

fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}


fun convertKtoC(temp: Double): String{
    val df = DecimalFormat("#.##C")
    return df.format(temp.minus( 273.15))
}