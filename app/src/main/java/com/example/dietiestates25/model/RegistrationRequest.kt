package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest (
    val email: String,
    val role: String
)