package com.example.dietiestates25.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.BuildConfig
import com.example.dietiestates25.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class SearchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initPlace()

        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.DISPLAY_NAME))

        // Limit the autocomplete
        autocompleteFragment.setLocationBias(
            RectangularBounds.newInstance(
                LatLng(36.619987, 6.627265), // Sud-ovest dell'Italia
                LatLng(47.092146, 18.513410) // Nord-est dell'Italia
            )
        )

        autocompleteFragment.setCountries("IT")
        autocompleteFragment.setTypesFilter(listOf(PlaceTypes.CITIES))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.id}, ${place.displayName}")
                //invia al backend
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initPlace() {
        val apiKey = BuildConfig.MAPS_API_KEY
        if (apiKey.isEmpty()) {
            Log.e("Places test", "No api key")
            finish()
            return
        }

        if (!Places.isInitialized()){
            Places.initialize(applicationContext, apiKey)
        }
    }

    companion object {
        private const val TAG = "SearchActivity"
    }

}