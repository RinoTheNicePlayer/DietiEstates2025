package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class Utente(
    val email: String,
    val password: String,
    val group: String
)