package com.example.androidtest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtest.models.CurrentWeatherInformationUIModel
import com.example.androidtest.models.CurrentWeatherResponse
import com.example.androidtest.models.WeatherForcastInformationUIModel
import com.example.androidtest.models.WeatherResponse
import com.example.androidtest.repo.CurrentWeatherListener
import com.example.androidtest.repo.DataDownloadListener
import com.example.androidtest.repo.Repo
import com.example.androidtest.utils.convertDateFormat
import com.example.androidtest.utils.convertKtoC
import com.example.androidtest.utils.toDateString
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repo: Repo) : ViewModel() {
    var weatherForcastLiveData: MutableLiveData<List<WeatherForcastInformationUIModel>> = MutableLiveData()
    var currentLiveData: MutableLiveData<CurrentWeatherInformationUIModel> = MutableLiveData()

    fun getWeatherData(placeName: String) {
        repo.getWeatherData(listener, placeName)
    }

    fun getCurrentData(placeName: String) {
        repo.getCurrentData(currentWeatherListener, placeName)
    }

    var currentWeatherListener : CurrentWeatherListener = object : CurrentWeatherListener {
        override fun onFailure(message: String) {

        }

        override fun onSuccess(response: CurrentWeatherResponse?) {
            response?.let{
                currentLiveData.value = processCurrentWeatherIntoInfo(it)
            }
        }

    }

    var listener : DataDownloadListener? = object : DataDownloadListener {
        override fun onFailure(message: String) {
            weatherForcastLiveData.value = emptyList()
        }

        override fun onSuccess(list: List<WeatherResponse>) {
            processForecastDataIntoInfo(list)
        }

    }

    fun processCurrentWeatherIntoInfo(response: CurrentWeatherResponse): CurrentWeatherInformationUIModel {
        var description: String = ""
        response.weather.forEach {
            description += it.description + " "
        }
        return CurrentWeatherInformationUIModel(
            response.dt.toDateString(),
            response.name,
            convertKtoC(response.main.temp_min),
            convertKtoC(response.main.temp_max),
            convertKtoC(response.main.temp),
            description)
    }


    fun processForecastDataIntoInfo(list: List<WeatherResponse>){
        val weatherForcastInformationUIModel : ArrayList<WeatherForcastInformationUIModel>  = arrayListOf()
        if(!list.isNullOrEmpty()) {
            list.forEach {
                var description = ""
                it.weather.forEach { obj -> description += obj.description }
                weatherForcastInformationUIModel.add(WeatherForcastInformationUIModel(convertDateFormat(it.dt_txt), convertKtoC(it.main.temp_min), convertKtoC(it.main.temp_max), description,
                    it.weather.get(0).icon))
            }

        }
        weatherForcastLiveData.value = weatherForcastInformationUIModel
    }


    override fun onCleared() {
        super.onCleared()
        listener = null
    }
}