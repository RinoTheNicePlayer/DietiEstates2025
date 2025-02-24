package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    @SerialName("dataVisita")
    val date: String,
    @SerialName("oraVisita")
    val time: String,
    @SerialName("stato")
    val status: ReservationState,
    @SerialName("immobile")
    val property: PropertyResponse
)