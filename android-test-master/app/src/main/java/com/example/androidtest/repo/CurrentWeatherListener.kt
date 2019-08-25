package com.example.androidtest.repo

import com.example.androidtest.models.CurrentWeatherResponse

interface CurrentWeatherListener {
    fun onSuccess(list: CurrentWeatherResponse?)
    fun onFailure (message: String)
}