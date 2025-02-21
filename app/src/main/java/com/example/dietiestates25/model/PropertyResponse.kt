package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyResponse (
    @SerialName("idImmobile")
    val idProperty: Int,
    @SerialName("urlFoto")
    val pathImage: String,
    @SerialName("descrizione")
    val description: String,
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
    @SerialName("longitudine")
    val longitude: Double,
    @SerialName("latitudine")
    val latitude: Double,
    @SerialName("indirizzo")
    val address: String,
    @SerialName("comune")
    val municipality: String,
    @SerialName("piano")
    val floor: Int,
    @SerialName("hasAscensore")
    val hasElevator: Boolean,
    @SerialName("hasBalcone")
    val hasBalcony: Boolean,
    @SerialName("idResponsabile")
    val idResponsabile: String
)