package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val password: String,
    val group: String
)