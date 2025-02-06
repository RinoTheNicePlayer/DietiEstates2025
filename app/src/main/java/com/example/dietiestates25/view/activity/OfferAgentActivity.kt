package com.example.dietiestates25

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.model.Offer

class OfferAgentActivity : AppCompatActivity() {

    private lateinit var recyclerPendingOffers: RecyclerView
    private lateinit var recyclerConfirmedOffers: RecyclerView
    private lateinit var pendingOffersAdapter: PendingOffersAdapter
    private lateinit var confirmedOffersAdapter: ConfirmedOffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_agent)

        recyclerPendingOffers = findViewById(R.id.recycler_pending_offers)
        recyclerConfirmedOffers = findViewById(R.id.recycler_confirmed_offers)

        setupRecyclerViews()
        loadOffers()
    }

    private fun setupRecyclerViews() {
        recyclerPendingOffers.layoutManager = LinearLayoutManager(this)
        recyclerConfirmedOffers.layoutManager = LinearLayoutManager(this)

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