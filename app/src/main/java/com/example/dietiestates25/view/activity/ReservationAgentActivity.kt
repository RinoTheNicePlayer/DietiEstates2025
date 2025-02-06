package com.example.dietiestates25.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.ConfirmedReservationsAdapter
import com.example.dietiestates25.adapter.PendingReservationsAdapter
import com.example.dietiestates25.model.Reservation

class ReservationAgentActivity : AppCompatActivity() {

    private lateinit var pendingReservationsAdapter: PendingReservationsAdapter
    private lateinit var confirmedReservationsAdapter: ConfirmedReservationsAdapter
    private val pendingReservations = mutableListOf<Reservation>()
    private val confirmedReservations = mutableListOf<Reservation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_agent)

        val recyclerPending = findViewById<RecyclerView>(R.id.recycler_pending_reservations)
        val recyclerConfirmed = findViewById<RecyclerView>(R.id.recycler_confirmed_reservations)

        recyclerPending.layoutManager = LinearLayoutManager(this)
        recyclerConfirmed.layoutManager = LinearLayoutManager(this)

        loadReservations()

        pendingReservationsAdapter = PendingReservationsAdapter(pendingReservations, {}, {})
        confirmedReservationsAdapter = ConfirmedReservationsAdapter(confirmedReservations)

        recyclerPending.adapter = pendingReservationsAdapter
        recyclerConfirmed.adapter = confirmedReservationsAdapter
    }

    private fun loadReservations() {
        pendingReservations.add(
            Reservation(
                "Immobile #7",
                "Sofia Conti",
                "15/01/25",
                "09:00",
                "pending"
            )
        )
        confirmedReservations.add(
            Reservation(
                "Immobile #4",
                "Giulia Ferrari",
                "04/01/25",
                "10:00",
                "confirmed"
            )
        )
    }
}