package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservationResponse (
    @SerialName("idVisita")
    val id: Int,
    @SerialName("dataVisita")
    val date: String,
    @SerialName("oraVisita")
    val time: String,
    @SerialName("statoVisita")
    val state: ReservationState,
    @SerialName("immobile")
    val property: PropertyResponse
)