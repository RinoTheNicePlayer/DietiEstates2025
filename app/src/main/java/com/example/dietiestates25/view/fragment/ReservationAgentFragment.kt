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
import com.example.dietiestates25.model.ReservationTest

class ReservationAgentFragment : Fragment() {
    private lateinit var pendingReservationsAdapter: PendingReservationsAdapter
    private lateinit var confirmedReservationsAdapter: ConfirmedReservationsAdapter
    private val pendingReservationTests = mutableListOf<ReservationTest>()
    private val confirmedReservationTests = mutableListOf<ReservationTest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_agent, container, false)

        val recyclerPending = view.findViewById<RecyclerView>(R.id.recycler_pending_reservations)
        val recyclerConfirmed = view.findViewById<RecyclerView>(R.id.recycler_confirmed_reservations)

        recyclerPending.layoutManager = LinearLayoutManager(context)
        recyclerConfirmed.layoutManager = LinearLayoutManager(context)

        loadReservations()

        pendingReservationsAdapter = PendingReservationsAdapter(pendingReservationTests, {}, {})
        confirmedReservationsAdapter = ConfirmedReservationsAdapter(confirmedReservationTests)

        recyclerPending.adapter = pendingReservationsAdapter
        recyclerConfirmed.adapter = confirmedReservationsAdapter

        return view
    }

    private fun loadReservations() {
        pendingReservationTests.add(
            ReservationTest(
                "Immobile #7",
                "Sofia Conti",
                "15/01/25",
                "09:00",
                "pending"
            )
        )
        confirmedReservationTests.add(
            ReservationTest(
                "Immobile #4",
                "Giulia Ferrari",
                "04/01/25",
                "10:00",
                "confirmed"
            )
        )
    }
}