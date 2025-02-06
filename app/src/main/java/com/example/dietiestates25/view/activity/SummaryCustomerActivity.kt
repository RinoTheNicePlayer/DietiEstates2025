package com.example.dietiestates25.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapters.SentOffersAdapter
import com.example.dietiestates25.adapters.VisitedReservationsAdapter
import com.example.dietiestates25.models.SentOffer
import com.example.dietiestates25.models.VisitedReservation

class SummaryCustomerActivity : AppCompatActivity() {

    private lateinit var sentOffersAdapter: SentOffersAdapter
    private lateinit var visitedReservationsAdapter: VisitedReservationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary_customer)

        val sentOffersRecyclerView: RecyclerView = findViewById(R.id.sent_offers_recycler_view)
        sentOffersRecyclerView.layoutManager = LinearLayoutManager(this)
        sentOffersAdapter = SentOffersAdapter(emptyList())
        sentOffersRecyclerView.adapter = sentOffersAdapter

        val visitedReservationsRecyclerView: RecyclerView =
            findViewById(R.id.visited_reservations_recycler_view)
        visitedReservationsRecyclerView.layoutManager = LinearLayoutManager(this)
        visitedReservationsAdapter = VisitedReservationsAdapter(emptyList())
        visitedReservationsRecyclerView.adapter = visitedReservationsAdapter

        fetchSentOffers()
        fetchVisitedReservations()
    }

    private fun fetchSentOffers() {
        val sentOffers = listOf(
            SentOffer("Immobile #4", "Giulia Ferrari", 175000.0),
            SentOffer("Immobile #5", "Maria Esposito", 295000.0),
            SentOffer("Immobile #6", "Davide Ricci", 220000.0)
        )
        sentOffersAdapter.updateList(sentOffers)
    }

    private fun fetchVisitedReservations() {
        val visitedReservations = listOf(
            VisitedReservation("Immobile #4", "Giulia Ferrari", "04/01/25", "10:00"),
            VisitedReservation("Immobile #11", "Giulia Ferrari", "07/01/25", "11:00"),
            VisitedReservation("Immobile #12", "Giulia Ferrari", "12/01/25", "09:00")
        )
        visitedReservationsAdapter.updateList(visitedReservations)
    }
}