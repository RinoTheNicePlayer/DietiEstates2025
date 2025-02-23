package com.example.dietiestates25.controller

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.dietiestates25.R
import com.example.dietiestates25.model.Offer
import com.example.dietiestates25.model.PropertyResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class OfferController(private val context: Context) {
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

    fun sendOffer(offer: Offer, onSuccess: () -> Unit) {
        val client = OkHttpClient()
        val token = AuthManager.idToken
        val url = "/offerta/aggiungi" ///da cambiare

        val json = Json.encodeToString(offer)
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