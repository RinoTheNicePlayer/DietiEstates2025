package com.example.dietiestates25.controller

import android.os.Handler
import android.os.Looper
import android.content.Context
import android.util.Log
import com.example.dietiestates25.model.Address
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class HomeCustomerController(private val context: Context) {

    /// TODO: gestire response e andare nella schermata della lista di immobili, quindi passare il response alla prossima schermata
    fun searchPropertyFromAddress(address: String, callback: (Boolean) -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.instance?.idToken

        val jsonBody = Json.encodeToString(Address(address))
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("")  // 🔹 Sostituisci con il tuo URL
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("AddressValidation", "Errore di rete: ${e.message}")
                Handler(Looper.getMainLooper()).post {
                    callback(false)  // Mostra errore
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    Handler(Looper.getMainLooper()).post {
                        callback(true)  // Indirizzo valido → chiudi la Bottom Sheet
                    }
                } else {
                    Log.e("AddressValidation", "Errore HTTP: ${response.code}")
                    Handler(Looper.getMainLooper()).post {
                        callback(false)  // Mostra errore
                    }
                }
            }
        })
    }
}