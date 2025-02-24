package com.example.dietiestates25.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfferResponse (
    @SerialName("idOfferta")
    val id: Int,
    @SerialName("importo")
    val amount: Double,
    @SerialName("stato")
    var state: OfferState,
    @SerialName("idCliente")
    val idCustomer: String,
    @SerialName("immobile")
    val property: PropertyResponse
)