package com.example.dietiestates25.controller

import android.content.Context
import android.net.Uri
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StoragePath
import java.io.File

class S3Controller(private val context: Context) {
    fun uploadInputStream(uri: Uri) {
        val stream = context.contentResolver.openInputStream(uri)

        if (stream != null) {
            Amplify.Storage.uploadInputStream(
                StoragePath.fromString("public/example"),
                stream,
                { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.path}") },
                { Log.e("MyAmplifyApp", "Upload failed", it) }
            )
        }
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

    fun getUrlFromStoragePath() {
        Amplify.Storage.getUrl(
            StoragePath.fromString("public/example"),
            { Log.i("MyAmplifyApp", "Successfully generated: ${it.url}") },
            { Log.e("MyAmplifyApp", "URL generation failure", it) }
        )
    }

}