package com.example.dietiestates25.controller

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.dietiestates25.model.PropertyResponse
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URLEncoder

class HomeCustomerController {

    fun searchPropertyFromAddress(
        address: String,
        type: String? = null,
        priceMin: Double? = null,
        priceMax: Double? = null,
        size: Double? = null,
        nBathrooms: Int? = null,
        callback: (List<PropertyResponse>?) -> Unit
    ) {
        val client = OkHttpClient()
        val token = AuthManager.idToken
        val urlBuilder = stringBuilder(address, type, priceMin, priceMax, size, nBathrooms)

        val request = Request.Builder()
            .url(urlBuilder.toString())
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .get()
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("AddressValidation", "Errore di rete: ${e.message}")
                Handler(Looper.getMainLooper()).post {
                    callback(null)  // Mostra errore
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body.string()
                    val properties = Json.decodeFromString<List<PropertyResponse>>(responseBody)

                    Handler(Looper.getMainLooper()).post {
                        callback(properties)  // Indirizzo valido → immobili trovati
                    }
                } else {
                    Log.e("AddressValidation", "Errore HTTP: ${response.code}")
                    Handler(Looper.getMainLooper()).post {
                        callback(null)  // Mostra errore
                    }
                }
            }
        })
    }

    private fun stringBuilder(
        address: String,
        type: String?,
        priceMin: Double?,
        priceMax: Double?,
        size: Double?,
        nBathrooms: Int?
    ): StringBuilder {
        /// cambiare il primo url con ip
        val urlBuilder = StringBuilder("/immobile/cerca?comune=${URLEncoder.encode(address, "UTF-8")}")
        type?.let { urlBuilder.append("&tipologia=${URLEncoder.encode(it, "UTF-8")}") }
        priceMin?.let { urlBuilder.append("&prezzoMin=$it") }
        priceMax?.let { urlBuilder.append("&prezzoMax=$it") }
        size?.let { urlBuilder.append("&dimensione=$it") }
        nBathrooms?.let { urlBuilder.append("&nBagni=$it") }

        return urlBuilder
    }
}