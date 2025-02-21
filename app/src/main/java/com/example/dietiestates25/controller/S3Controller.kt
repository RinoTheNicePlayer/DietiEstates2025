package com.example.dietiestates25.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StoragePath
import com.example.dietiestates25.model.Property
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.UUID

class S3Controller(private val context: Context) {
    fun uploadInputStream(uri: Uri): String {
        val stream = context.contentResolver.openInputStream(uri)
        val uniquePath = "public/${UUID.randomUUID()}.jpg"

        if (stream != null) {
            Amplify.Storage.uploadInputStream(
                StoragePath.fromString(uniquePath),
                stream,
                { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.path}") },
                { Log.e("MyAmplifyApp", "Upload failed", it) }
            )
        }

        return uniquePath
    }

    fun uploadDefaultImage(imageView: ImageView): String {
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val uniquePath = "public/${UUID.randomUUID()}.jpg"

        Amplify.Storage.uploadInputStream(
            StoragePath.fromString(uniquePath),
            inputStream,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.path}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )

        return uniquePath
    }

    fun downloadToFile(path: String) {
        val file = File("${context.applicationContext.filesDir}/downloaded_image.jpg")

        Amplify.Storage.downloadFile(
            StoragePath.fromString(path),
            file,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp",  "Download Failure", it) }
        )
    }

    fun getUrlFromStoragePath(path: String) {
        Amplify.Storage.getUrl(
            StoragePath.fromString(path),
            { Log.i("MyAmplifyApp", "Successfully generated: ${it.url}") },
            { Log.e("MyAmplifyApp", "URL generation failure", it) }
        )
    }

    fun saveProperty(property: Property, onSuccess: () -> Unit) {
        val client = OkHttpClient()
        val url = "/immobile/crea" /// TODO: da cambiare
        val token = AuthManager.idToken

        val json = Json.encodeToString(property)
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