package com.example.dietiestates25.controller

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.model.Address
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class HomeClienteController(private val context: Context) {
    fun initPlace(activity: AppCompatActivity) {
        val apiKey = BuildConfig.MAPS_API_KEY
        if (apiKey.isEmpty()) {
            Log.e("Places test", "No api key")
            activity.finish()
            return
        }

        if (!Places.isInitialized()){
            Places.initialize(context.applicationContext, apiKey)
        }
    }

    fun setLimitAutoComplete(autocompleteFragment: AutocompleteSupportFragment) {
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS_COMPONENTS))

        // Limit the autocomplete
        autocompleteFragment.setLocationBias(
            RectangularBounds.newInstance(
                LatLng(36.619987, 6.627265), // Sud-ovest dell'Italia
                LatLng(47.092146, 18.513410) // Nord-est dell'Italia
            )
        )
        autocompleteFragment.setCountries("IT")
        autocompleteFragment.setTypesFilter(listOf(PlaceTypes.CITIES, PlaceTypes.ADDRESS))
    }

    /// TODO: gestire response e andare nella schermata della lista di immobili, quindi passare il response alla prossima schermata
    fun searchImmobileFromAddress(address: Address) {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = Json.encodeToString(address).toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://your-backend-url.com/endpoint") /// TODO: Cambiare URL
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("Errore invio indirizzo", "Failed to send display name to backend: ${response.message}")
            } else {
                Log.i("Indirizzo inviato", "Successfully sent display name to backend")
            }
        }

    }
}