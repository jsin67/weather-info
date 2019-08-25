package com.example.androidtest.repo

import com.example.androidtest.models.WeatherResponse

interface LocationListener {
    fun onSuccess()

    fun onFailure (message: String)
}