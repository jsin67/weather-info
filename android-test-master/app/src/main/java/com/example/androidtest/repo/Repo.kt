package com.example.androidtest.repo

interface Repo {

    fun getWeatherData(listener: DataDownloadListener?, placeName: String)
    fun getCurrentData(listener: CurrentWeatherListener, placeName: String)
    fun addLocation(listener: LocationListener, placeName: String)
    fun getAllLocation(): List<String>
}