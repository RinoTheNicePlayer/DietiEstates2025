package com.example.dietiestates25.model

enum class EnergeticClassType(val classe: String) {
    A_PLUS_PLUS("A++"),
    A_PLUS("A+"),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G");

    companion object {
        /** Probabilmente non serve. Tenere fino a quando non si è sicuri */
        fun fromString(classe: String): EnergeticClassType {
            for (c in entries) {
                if (c.classe.equals(classe, ignoreCase = true)) {
                    return c
                }
            }
            throw IllegalArgumentException("Classe energetica non valida: $classe")
        }
    }
}