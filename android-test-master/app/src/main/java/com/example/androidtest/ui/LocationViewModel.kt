package com.example.androidtest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtest.repo.LocationListener
import com.example.androidtest.repo.Repo
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val repo: Repo) : ViewModel() {
    var locationLiveData: MutableLiveData<List<String>> = MutableLiveData()


    fun addLocation(value: String){
       repo.addLocation(listener, value)
    }


    fun getAllLocation(){
        locationLiveData.value = repo.getAllLocation()
    }

    var listener : LocationListener = object : LocationListener {
        override fun onFailure(message: String) {
        }

        override fun onSuccess() {
            getAllLocation()
        }

    }
    override fun onCleared() {
        super.onCleared()
    }
}