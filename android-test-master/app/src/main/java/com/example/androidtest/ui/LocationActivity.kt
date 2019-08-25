package com.example.androidtest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R
import com.example.androidtest.di.Injectable
import com.example.androidtest.ui.adapter.LocationListAdapter
import kotlinx.android.synthetic.main.location_layout.*
import javax.inject.Inject

class LocationActivity : AppCompatActivity(), Injectable, OnLocationTappedListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LocationViewModel
    private val adapter = LocationListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel::class.java)
        setContentView(R.layout.location_layout)
        setupView()
        viewModel.locationLiveData.observe(this, Observer { locationList ->
            if (locationList != null) {
                adapter.setLocationData(locationList)
            }else {
                Toast.makeText(this, getString(R.string.data_error), Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getAllLocation()
        val edtLocation: EditText = findViewById(R.id.edt_location)
        findViewById<Button>(R.id.btn_add_location).setOnClickListener {
            viewModel.addLocation(edtLocation.text.toString())
        }
    }

    /**
     * Sets up views
     */
    private fun setupView() {
        rcy_locations.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(it.context, RecyclerView.VERTICAL))
        }
    }

    override fun onItemTapped(value: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("location", value)
        startActivity(intent)
    }

}
