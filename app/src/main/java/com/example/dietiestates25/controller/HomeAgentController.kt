package com.example.dietiestates25.controller

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.dietiestates25.model.PropertyResponse
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HomeAgentController {
    fun getMyProperties(callback: (List<PropertyResponse>?) -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken

        val request = Request.Builder()
            .url("/immobile/personali")  /// Sostituisci con il tuo URL
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