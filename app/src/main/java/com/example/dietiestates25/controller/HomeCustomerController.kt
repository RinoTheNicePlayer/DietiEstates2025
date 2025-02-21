package com.example.dietiestates25.controller

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.dietiestates25.model.PropertyResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class HomeCustomerController {

    fun searchPropertyFromAddress(address: String, callback: (List<PropertyResponse>?) -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken

        val jsonBody = Json.encodeToString(mapOf("comune" to address))
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("/immobile/cerca")  /// Sostituisci con il tuo URL
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
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