package com.example.dietiestates25.controller

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.dietiestates25.model.MeteoRequest
import com.example.dietiestates25.model.Reservation
import com.example.dietiestates25.model.ReservationResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class ReservationController {
    fun getMeteo(meteoRequest: MeteoRequest, callback: (Map<String, Any>?) -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken
        val url = "/api/meteo" // da cambiare

        val json = Json.encodeToString(meteoRequest)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("Backend", "Failed to send data", e)
                callback(null)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body.string()
                    val result: Map<String, Any> = Json.decodeFromString(responseBody)

                    Log.i("Backend", "Data sent successfully: $responseBody")
                    callback(result)
                } else {
                    Log.e("Backend", "Failed to send data: ${response.message}")
                    callback(null)
                }
            }
        })
    }

    fun saveReservation(reservation: Reservation, onSuccess: () -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken
        val url = "/visita/prenota" // da cambiare

        val json = Json.encodeToString(reservation)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

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
                    val responseBody = response.body.string()
                    Log.i("Backend", "Data sent successfully: $responseBody")
                    onSuccess()
                } else {
                    Log.e("Backend", "Failed to send data: ${response.message}")
                }
            }
        })
    }

    fun getMyReservation(callback: (List<ReservationResponse>?) -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken
        val url = "/visita/riepilogo" // da cambiare

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .get()
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("Backend", "Failed to fetch data", e)
                Handler(Looper.getMainLooper()).post {
                    callback(null)  // Mostra errore
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body.string()
                    val reservations = Json.decodeFromString<List<ReservationResponse>>(responseBody)

                    Handler(Looper.getMainLooper()).post {
                        callback(reservations)
                    }
                    Log.i("Backend", "Data fetched successfully: $responseBody")
                } else {
                    Log.e("Backend", "Failed to fetch data: ${response.message}")
                    Handler(Looper.getMainLooper()).post {
                        callback(null)  // Mostra errore
                    }
                }
            }
        })
    }

}