package com.example.dietiestates25.controller

import okhttp3.OkHttpClient

object HttpClient {
    val client: OkHttpClient = OkHttpClient.Builder()
        .build()
}