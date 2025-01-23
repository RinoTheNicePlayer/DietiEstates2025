package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val description: String,
    val price: Double,
    val nBathroom: Int,
    val nRoom: Int,
    val type: String,
    val energeticClass: String, /// TODO: fare enum
    val floor: Int,
    val hasElevator: Boolean,
    val hasBalcone: Boolean,
    val address: Address
)