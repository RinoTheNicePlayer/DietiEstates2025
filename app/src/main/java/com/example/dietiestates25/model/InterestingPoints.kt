package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InterestingPoints (
    @SerialName("latitudine")
    val latitude: Double,
    @SerialName("longitudine")
    val longitude: Double,
    @SerialName("raggio")
    val radius: Int = 500,
    @SerialName("categorie")
    val classes: List<String> = listOf("leisure.park", "public_transport", "education.school")
)