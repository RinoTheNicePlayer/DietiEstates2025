package com.example.dietiestates25.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietiestates25.model.Reservation

class ReservationViewModel: ViewModel() {
    private val _reservation = MutableLiveData<Reservation>()
    val selectedReservation: LiveData<Reservation> get() = _reservation

    fun selectReservation(reservation: Reservation) {
        _reservation.value = reservation
    }
}