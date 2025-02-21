package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Property (
    @SerialName("descrizione")
    val description: String,
    @SerialName("urlFoto")
    val pathImage: String,
    @SerialName("prezzo")
    val price: Double,
    @SerialName("dimensione")
    val size: Int,
    @SerialName("nBagni")
    val nBathrooms: Int,
    @SerialName("nStanze")
    val nRooms: Int,
    @SerialName("tipologia")
    val type: String,
    @SerialName("indirizzo")
    val address: String,
    @SerialName("comune")
    val municipality: String,
    @SerialName("piano")
    val floor: Int,
    @SerialName("hasAscensore")
    val hasElevator: Boolean,
    @SerialName("hasBalcone")
    val hasBalcony: Boolean
)