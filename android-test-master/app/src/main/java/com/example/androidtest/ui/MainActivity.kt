package com.example.androidtest.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R
import com.example.androidtest.di.Injectable
import com.example.androidtest.models.CurrentWeatherInformationUIModel
import com.example.androidtest.models.CurrentWeatherResponse
import com.example.androidtest.models.WeatherResponse
import com.example.androidtest.ui.adapter.WeatherListAdapter
import com.example.androidtest.utils.verifyAvailableNetwork
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable, OnWeatherTappedListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    private val adapter = WeatherListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        setupView()
        observeForcastData()
        observeCurrentData()


        val location = intent.extras.getString("location")
        if(verifyAvailableNetwork(this) && location.isNotBlank() ) {
            showProgressing()
            viewModel.getCurrentData(location)
            viewModel.getWeatherData(location)
        }else {
            Toast.makeText(this, getString(R.string.internet_warning), Toast.LENGTH_LONG).show()
        }


    }

    private fun observeCurrentData() {
        viewModel.currentLiveData.observe(this, Observer { currentData ->
            if (currentData != null) {
                setCurrentData(currentData)
            } else {
                Toast.makeText(this, getString(R.string.data_error), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setCurrentData(data: CurrentWeatherInformationUIModel){
        findViewById<TextView>(R.id.tv_current_location).text = data.currentLocation
        findViewById<TextView>(R.id.tv_current_date).text = data.date
        findViewById<TextView>(R.id.tv_current_min).text = data.minTemp
        findViewById<TextView>(R.id.tv_current_max).text = data.maxTemp
        findViewById<TextView>(R.id.tv_current_temp).text = data.temp
        findViewById<TextView>(R.id.tv_current_description).text = data.weatherDescription
    }

    private fun observeForcastData() {
        viewModel.weatherForcastLiveData.observe(this, Observer { weatherInfoList ->
            if (weatherInfoList != null) {
                adapter.setWeatherData(weatherInfoList)
            }else {
                Toast.makeText(this, getString(R.string.data_error), Toast.LENGTH_LONG).show()
            }
            dismissProgressing()
        })
    }

    /**
     * Sets up views
     */
    private fun setupView() {
        recycler_view.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(it.context, RecyclerView.VERTICAL))
        }
    }

    /**
     * Show progress
     */
    private fun showProgressing() {
        LoadingDialogFragment().show(supportFragmentManager, LoadingDialogFragment.TAG)
    }

    /**
     * Dismiss progress
     */
    private fun dismissProgressing() {
        supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG)
            ?.takeIf { it is DialogFragment }
            ?.run {
                (this as DialogFragment).dismiss()
            }
    }

    /**
     * Callback when weatherResponse is selected
     * @param weatherResponse: Selected Item
     */
    override fun onItemTapped(weatherResponse: WeatherResponse) {

    }
}
