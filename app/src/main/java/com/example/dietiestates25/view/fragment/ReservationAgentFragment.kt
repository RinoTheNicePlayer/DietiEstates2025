package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.ConfirmedReservationsAdapter
import com.example.dietiestates25.adapter.PendingReservationsAdapter
import com.example.dietiestates25.controller.ReservationController
import com.example.dietiestates25.model.ReservationResponse
import com.example.dietiestates25.model.ReservationState

class ReservationAgentFragment : Fragment() {
    private lateinit var pendingReservationsAdapter: PendingReservationsAdapter
    private lateinit var confirmedReservationsAdapter: ConfirmedReservationsAdapter
    private lateinit var reservationController: ReservationController
    private val pendingReservations = mutableListOf<ReservationResponse>()
    private val confirmedReservations= mutableListOf<ReservationResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservationController = ReservationController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_agent, container, false)

        // trovo i recycler
        val recyclerPending = view.findViewById<RecyclerView>(R.id.recycler_pending_reservations)
        val recyclerConfirmed = view.findViewById<RecyclerView>(R.id.recycler_confirmed_reservations)
        recyclerPending.layoutManager = LinearLayoutManager(context)
        recyclerConfirmed.layoutManager = LinearLayoutManager(context)

        //buildo gli adapter
        loadReservations()

        // assegno adapter
        pendingReservationsAdapter = PendingReservationsAdapter(pendingReservations)
        confirmedReservationsAdapter = ConfirmedReservationsAdapter(confirmedReservations)
        recyclerPending.adapter = pendingReservationsAdapter
        recyclerConfirmed.adapter = confirmedReservationsAdapter

        return view
    }

    private fun loadReservations() {
        reservationController.getReservationFromAgency { reservations ->
            reservations?.let { reservationsResponse ->
                reservationsResponse.forEach {
                    if (it.state == ReservationState.IN_SOSPESO) {
                        pendingReservations.add(it)
                    } else if (it.state == ReservationState.CONFERMATA) {
                        confirmedReservations.add(it)
                    }
                }
            }
        }
    }

}