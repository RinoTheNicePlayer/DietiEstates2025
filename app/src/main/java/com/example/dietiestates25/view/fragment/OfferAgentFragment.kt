package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.ConfirmedOffersAdapter
import com.example.dietiestates25.PendingOffersAdapter
import com.example.dietiestates25.R
import com.example.dietiestates25.model.Offer

class OfferAgentFragment : Fragment() {
    private lateinit var recyclerPendingOffers: RecyclerView
    private lateinit var recyclerConfirmedOffers: RecyclerView
    private lateinit var pendingOffersAdapter: PendingOffersAdapter
    private lateinit var confirmedOffersAdapter: ConfirmedOffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offer_agent, container, false)

        recyclerPendingOffers = view.findViewById(R.id.recycler_pending_offers)
        recyclerConfirmedOffers = view.findViewById(R.id.recycler_confirmed_offers)

        setupRecyclerViews()
        loadOffers()

        return view
    }

    private fun setupRecyclerViews() {
        recyclerPendingOffers.layoutManager = LinearLayoutManager(context)
        recyclerConfirmedOffers.layoutManager = LinearLayoutManager(context)

        pendingOffersAdapter = PendingOffersAdapter(emptyList())
        confirmedOffersAdapter = ConfirmedOffersAdapter(emptyList())

        recyclerPendingOffers.adapter = pendingOffersAdapter
        recyclerConfirmedOffers.adapter = confirmedOffersAdapter
    }

    private fun loadOffers() {
        // Simulazione del caricamento dei dati (da sostituire con chiamata al database)
        val pendingOffers = listOf(
            Offer("Immobile #1", "Alessandro Rossi", 65000.0.toString()),
            Offer("Immobile #2", "Marco Bianchi", 120000.0.toString()),
            Offer("Immobile #3", "Luca Verdi", 85000.0.toString())
        )

        val confirmedOffers = listOf(
            Offer("Immobile #4", "Giulia Ferrari", 175000.0.toString()),
            Offer("Immobile #5", "Maria Esposito", 295000.0.toString()),
            Offer("Immobile #6", "Davide Ricci", 220000.0.toString())
        )

        pendingOffersAdapter.updateList(pendingOffers)
        confirmedOffersAdapter.updateList(confirmedOffers)
    }
}