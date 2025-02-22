package com.example.dietiestates25.controller

import android.content.Context
import android.widget.ImageView
import coil.load
import com.example.dietiestates25.R
import com.example.dietiestates25.model.PropertyResponse
import okhttp3.OkHttpClient

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

    fun getInterestingPoints() {
        val client = OkHttpClient()
    }
}