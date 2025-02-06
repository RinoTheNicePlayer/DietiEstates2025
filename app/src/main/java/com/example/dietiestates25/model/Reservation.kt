package com.example.dietiestates25.model

data class Reservation(
    val propertyName: String,
    val clientName: String,
    val date: String,
    val time: String,
    val status: String
)