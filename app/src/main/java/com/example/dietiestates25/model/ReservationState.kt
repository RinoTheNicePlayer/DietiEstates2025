package com.example.dietiestates25.model

enum class ReservationState(val stato: String) {
    CONFERMATA("Confermata"),
    RIFIUTATA("Rifiutata"),
    IN_SOSPESO("In sospeso");

    companion object {
        fun fromString(stato: String): ReservationState {
            return ReservationState.entries.find { it.stato.equals(stato, ignoreCase = true) }
                ?: throw IllegalArgumentException("Stato visita non valida: $stato")
        }
    }
}