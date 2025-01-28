package com.example.dietiestates25.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.controller.HomeClienteController
import com.example.dietiestates25.R
import com.example.dietiestates25.model.Address
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class HomeClienteActivity: AppCompatActivity() {
    private lateinit var homeClienteController: HomeClienteController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_customer)
        homeClienteController = HomeClienteController(this)
        initPlace()

        //val searchButton = findViewById<LinearLayout>(R.id.searchButton)
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        setAutoCompleteLimit(autocompleteFragment)

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Get info about the selected place and search.
                searchImmobile(takePlaceAddress(place))
            }

            override fun onError(status: Status) {
                Log.e("no indirizzo selezionato", "An error occurred: $status")
            }
        })
    }

    private fun initPlace() {
        homeClienteController.initPlace(this)
    }

    private fun setAutoCompleteLimit(autocompleteFragment: AutocompleteSupportFragment) {
        homeClienteController.setLimitAutoComplete(autocompleteFragment)
    }

    private fun searchImmobile(address: Address) {
        homeClienteController.searchImmobileFromAddress(address)
    }

    private fun takePlaceAddress(place: Place): Address {
        val cityName = place.addressComponents?.asList()?.find { it.types.contains("locality") }?.name ?: ""
        val streetName = place.addressComponents?.asList()?.find { it.types.contains("route") }?.name ?: ""
        val cap = place.addressComponents?.asList()?.find { it.types.contains("postal_code") }?.name ?: ""

        return Address(streetName, cityName, cap)
    }
}