package com.example.dietiestates25.controller

import android.content.Context
import android.net.Uri
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StoragePath
import java.io.File
import java.util.UUID

class S3Controller(private val context: Context) {
    //uniquePath è da salvare
    fun uploadInputStream(uri: Uri): String? {
        val stream = context.contentResolver.openInputStream(uri)
        val uniquePath = "public/${UUID.randomUUID()}.jpg"

        if (stream != null) {
            Amplify.Storage.uploadInputStream(
                StoragePath.fromString(uniquePath),
                stream,
                { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.path}") },
                { Log.e("MyAmplifyApp", "Upload failed", it) }
            )

            return uniquePath
        }

        return null
    }

    fun downloadToFile() {
        val file = File("${context.applicationContext.filesDir}/example.txt")

        Amplify.Storage.downloadFile(
            StoragePath.fromString("public/example"),
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

}