package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Offer (
    @SerialName("importo")
    val price: Double,
    @SerialName("stato")
    val state: OfferState,
    @SerialName("immobile")
    val property: PropertyResponse
)