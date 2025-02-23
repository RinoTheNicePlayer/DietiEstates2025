package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeteoRequest (
    @SerialName("latitudine")
    val latitude: Double,
    @SerialName("longitudine")
    val longitude: Double,
    @SerialName("date")
    val date: String
)