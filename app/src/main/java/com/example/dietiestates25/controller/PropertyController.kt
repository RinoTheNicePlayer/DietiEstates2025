package com.example.dietiestates25.controller

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.dietiestates25.model.Property
import com.example.dietiestates25.model.PropertyResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.URLEncoder

class PropertyController {
    fun saveProperty(property: Property, onSuccess: () -> Unit) {
        val client = HttpClient.client
        val url = "http://51.20.116.174:8080/immobile/crea" /// TODO: da cambiare

        val json = Json.encodeToString(property)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        AuthManager.getToken { token ->
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.e("Backend", "Failed to send data", e)
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody: ResponseBody = response.body
                        val responseMessage = responseBody.string()
                        Log.i("Backend", "Data sent successfully: $responseMessage")
                        onSuccess()
                    } else {
                        Log.e("Backend", "Failed to send data: ${response.message}")
                    }
                }
            })
        }
    }

    fun getMyProperties(callback: (List<PropertyResponse>?) -> Unit) {
        val client = HttpClient.client

        AuthManager.getToken { token ->
            val request = Request.Builder()
                .url("http://51.20.116.174:8080/immobile/personali")  /// Sostituisci con il tuo URL
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json")
                .get()
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.e("Recupero miei immobili ", "Errore di rete: ${e.message}")
                    Handler(Looper.getMainLooper()).post {
                        callback(null)  // Mostra errore
                    }
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body.string()
                        val properties = Json.decodeFromString<List<PropertyResponse>>(responseBody)

                        Handler(Looper.getMainLooper()).post {
                            callback(properties)  //immobili trovati
                        }
                    } else {
                        Log.e("Recupero miei immobili", "Errore HTTP: ${response.code}")
                        Handler(Looper.getMainLooper()).post {
                            callback(null)  // Mostra errore
                        }
                    }
                }
            })
        }
    }

    fun searchPropertyFromAddress(
        address: String,
        type: String? = null,
        priceMin: Double? = null,
        priceMax: Double? = null,
        size: Int? = null,
        nBathrooms: Int? = null,
        page: Int = 0,
        pageSize: Int = 5,
        callback: (List<PropertyResponse>?) -> Unit
    ) {
        if (!validateSearchParameters(address, type, priceMin, priceMax, size, nBathrooms)) {
            callback(null)
            return
        }
        val client = HttpClient.client
        val urlBuilder = stringBuilder(page, pageSize, address, type, priceMin, priceMax, size, nBathrooms)

        AuthManager.getToken { token ->
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
    }

    private fun stringBuilder(
        page: Int,
        pageSize: Int,
        address: String,
        type: String?,
        priceMin: Double?,
        priceMax: Double?,
        size: Int?,
        nBathrooms: Int?
    ): StringBuilder {
        /// cambiare il primo url con ip
        val urlBuilder = StringBuilder("http://51.20.116.174:8080/immobile/cerca?page=$page&size=$pageSize&comune=${URLEncoder.encode(address, "UTF-8")}")
        type?.let { urlBuilder.append("&tipologia=${URLEncoder.encode(it, "UTF-8")}") }
        priceMin?.let { urlBuilder.append("&prezzoMin=$it") }
        priceMax?.let { urlBuilder.append("&prezzoMax=$it") }
        size?.let { urlBuilder.append("&dimensione=$it") }
        nBathrooms?.let { urlBuilder.append("&nBagni=$it") }

        return urlBuilder
    }

    fun validateSearchParameters(
        address: String,
        type: String?,
        priceMin: Double?,
        priceMax: Double?,
        size: Int?,
        nBathrooms: Int?
    ): Boolean {
        if (address == "") {
            return false
        }
        if (type != null && (type == "" || (type != "In Affitto" && type != "In Vendita"))) {
            return false
        }
        if (priceMin != null && priceMin <= 0) {
            return false
        }
        if (priceMax != null && priceMax <= 0) {
            return false
        }
        if (size != null && size <= 0) {
            return false
        }
        if (nBathrooms != null && nBathrooms <= 0) {
            return false
        }
        return true
    }
}