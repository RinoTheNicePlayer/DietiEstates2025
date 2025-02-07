package com.example.dietiestates25.model

import kotlinx.serialization.Serializable

@Serializable
data class Property (
    val description: String,
    val pathImage: String,
    val price: Double,
    val nBathrooms: Int,
    val nRooms: Int,
    val type: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val floor: Int,
    val hasElevator: Boolean,
    val hasBalcony: Boolean
)