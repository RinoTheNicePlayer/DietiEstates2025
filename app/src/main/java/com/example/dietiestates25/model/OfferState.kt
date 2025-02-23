package com.example.dietiestates25.model

enum class OfferState(val stato: String) {
    ACCETTATA("Accettata"),
    RIFIUTATA("Rifiutata"),
    IN_SOSPESO("In sospeso");

    companion object {
        fun fromString(stato: String): OfferState {
            return entries.find { it.stato.equals(stato, ignoreCase = true) }
                ?: throw IllegalArgumentException("Stato offerta non valida: $stato")
        }
    }
}