package com.example.androidtest.models

data class CurrentWeatherResponse(
    val main: Main,
    val dt: Long,
    val name: String,
    val weather:  List<WeatherInfo>
)