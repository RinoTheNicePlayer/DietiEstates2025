package com.example.dietiestates25.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StoragePath
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
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

    fun getUrlFromStoragePath(path: String, callback: (String?) -> Unit) {
        Amplify.Storage.getUrl(
            StoragePath.fromString(path),
            {
                Log.i("MyAmplifyApp", "Successfully generated: ${it.url}")
                callback(it.url.toString())
            },
            {
                Log.e("MyAmplifyApp", "URL generation failure", it)
                callback(null)
            }
        )
    }

}