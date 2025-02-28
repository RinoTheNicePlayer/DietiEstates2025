package com.example.dietiestates25.controller

import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

object HttpClient {
    val client: OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(File("/dev/null"), 0))  // Disable caching
        .build()
}