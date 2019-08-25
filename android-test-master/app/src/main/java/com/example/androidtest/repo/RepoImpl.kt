package com.example.androidtest.repo

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.androidtest.rest.RestApi

class RepoImpl(private val restApi: RestApi, val sharedPreferences: SharedPreferences) : Repo {
    /**
     * Calls api to get data.
     * @param data: set data in this reference
     * @param placeName: api end point name
     */
    @SuppressLint("CheckResult")
    override fun getWeatherData(listener: DataDownloadListener?, placeName: String) {

        restApi.getWeatherData(placeName)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({ templates ->
                val list = templates.body()?.list ?: listOf()
                listener?.onSuccess(list)
            }, {
               listener?.onFailure(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun getCurrentData(listener: CurrentWeatherListener, placeName: String) {
        restApi.getCurrentWeatherData(placeName)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({ templates ->
                listener?.onSuccess(templates.body())
            }, {
                listener?.onFailure(it.localizedMessage)
            })
    }

//    override fun getCurrentData(listener: CurrentWeatherListener?, placeName: String) {
//
//        restApi.getCurrentWeatherData(placeName)
//            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
//            .subscribe({ templates ->
//                listener?.onSuccess(templates.body())
//            }, {
//                listener?.onFailure(it.localizedMessage)
//            })
//    }

    override fun addLocation(listener: LocationListener, placeName: String) {
        val editor = sharedPreferences.edit()
        editor.putString(placeName, placeName)
        editor.apply()
        listener.onSuccess()
    }

    override fun getAllLocation(): List<String> {
        val locations = arrayListOf<String>()
        val allEntries = sharedPreferences.getAll()
        for (entry in allEntries.entries) {
           locations.add(entry.component1())
        }

        return locations
    }
}