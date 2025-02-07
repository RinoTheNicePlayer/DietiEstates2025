package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest (
    val email: String,
    val password: String,
    val role: String
)