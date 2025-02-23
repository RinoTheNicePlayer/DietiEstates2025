package com.example.dietiestates25.controller

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.dietiestates25.R
import com.example.dietiestates25.model.PropertyResponse
import com.example.dietiestates25.model.InterestingPoints
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Request
import java.io.IOException

class PropertyDetailsController(private val context: Context) {
    private val s3Controller = S3Controller(context)

    fun loadImage(property: PropertyResponse, imageProperty: ImageView) {
        s3Controller.getUrlFromStoragePath(property.pathImage) { url ->
            if (url != null) {
                imageProperty.load(url) {
                    crossfade(true) // Effetto di transizione
                    placeholder(R.drawable.image2) // Placeholder mentre carica
                    error(R.drawable.image2) // Immagine di errore se fallisce
                }
            } else {
                imageProperty.setImageResource(R.drawable.image2)
            }
        }
    }

    fun getInterestingPoints(interestingPoints: InterestingPoints, interestingPoint: TextView) {
        getInterestingPointsFromBackend(interestingPoints) { result ->
            handleSetTextInterestingPoints(result, interestingPoint)
        }
    }

    private fun handleSetTextInterestingPoints(result: Map<String, Int>?, interestingPoint: TextView) {
        result?.let {
            val scuolaCount = it["scuola"] ?: 0
            val parcoCount = it["parco"] ?: 0
            val trasportoCount = it["trasporto"] ?: 0

            val messages = mutableListOf<String>()

            if (scuolaCount > 0) {
                messages.add("vicino a ${if (scuolaCount > 1) "$scuolaCount scuole" else "una scuola"}")
            }
            if (parcoCount > 0) {
                messages.add("vicino a ${if (parcoCount > 1) "$parcoCount parchi" else "un parco"}")
            }
            if (trasportoCount > 0) {
                messages.add("vicino a ${if (trasportoCount > 1) "$trasportoCount fermate" else "una fermata"}")
            }

            interestingPoint.text = if (messages.isNotEmpty()) {
                messages.joinToString(" e ")
            } else {
                "Nessun punto di interesse vicino"
            }
        }
    }

    private fun getInterestingPointsFromBackend(interestingPoints: InterestingPoints, callback: (Map<String, Int>?) -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken
        val url = "/geodata/conteggio-pdi" /// da cambiare

        val json = Json.encodeToString(interestingPoints)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("Interesting point validation", "Errore di rete: ${e.message}")
                Handler(Looper.getMainLooper()).post {
                    callback(null)  // Mostra errore
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body.string()
                    val result: Map<String, Int> = Json.decodeFromString(responseBody)

                    Handler(Looper.getMainLooper()).post {
                        callback(result)  // punti di interesse trovati
                    }
                } else {
                    Log.e("Interesting point validation", "Errore HTTP: ${response.code}")
                    Handler(Looper.getMainLooper()).post {
                        callback(null)  // Mostra errore
                    }
                }
            }
        })
    }
}