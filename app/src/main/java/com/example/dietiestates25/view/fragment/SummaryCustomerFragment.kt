package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapters.SentOffersAdapter
import com.example.dietiestates25.adapters.VisitedReservationsAdapter
import com.example.dietiestates25.models.SentOffer
import com.example.dietiestates25.models.VisitedReservation

class SummaryCustomerFragment : Fragment() {
    private lateinit var sentOffersAdapter: SentOffersAdapter
    private lateinit var visitedReservationsAdapter: VisitedReservationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_summary_customer, container, false)

        val sentOffersRecyclerView: RecyclerView = view.findViewById(R.id.sent_offers_recycler_view)
        sentOffersRecyclerView.layoutManager = LinearLayoutManager(context)
        sentOffersAdapter = SentOffersAdapter(emptyList())
        sentOffersRecyclerView.adapter = sentOffersAdapter

        val visitedReservationsRecyclerView: RecyclerView =
            view.findViewById(R.id.visited_reservations_recycler_view)
        visitedReservationsRecyclerView.layoutManager = LinearLayoutManager(context)
        visitedReservationsAdapter = VisitedReservationsAdapter(emptyList())
        visitedReservationsRecyclerView.adapter = visitedReservationsAdapter

        fetchSentOffers()
        fetchVisitedReservations()

        return view
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