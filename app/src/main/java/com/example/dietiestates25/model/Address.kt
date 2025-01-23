package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class Address (
    val via: String,
    val comune: String,
    val cap: String
)