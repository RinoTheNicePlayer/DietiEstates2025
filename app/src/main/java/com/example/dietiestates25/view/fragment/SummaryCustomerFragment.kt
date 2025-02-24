package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.SentOffersAdapter
import com.example.dietiestates25.adapter.VisitedReservationsAdapter
import com.example.dietiestates25.controller.OfferController
import com.example.dietiestates25.controller.ReservationController
import com.example.dietiestates25.model.OfferResponse
import com.example.dietiestates25.model.ReservationResponse

class SummaryCustomerFragment : Fragment() {
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var sentOffersAdapter: SentOffersAdapter
    private var offerSent = mutableListOf<OfferResponse>()
    private var reservationSent = mutableListOf<ReservationResponse>()
    private lateinit var visitedReservationsAdapter: VisitedReservationsAdapter
    private lateinit var offerController: OfferController
    private lateinit var reservationController: ReservationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offerController = OfferController(requireContext())
        reservationController = ReservationController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_summary_customer, container, false)

        /// trova i recycler
        recyclerView1 = view.findViewById(R.id.sent_offers_recycler_view)
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView2 = view.findViewById(R.id.visited_reservations_recycler_view)
        recyclerView2.layoutManager = LinearLayoutManager(context)

        /// builda gli adapter
        getMyOffers()
        getMyReservation()

        /// assegna adapter
        sentOffersAdapter = SentOffersAdapter(offerSent)
        recyclerView1.adapter = sentOffersAdapter
        visitedReservationsAdapter = VisitedReservationsAdapter(reservationSent)
        recyclerView2.adapter = visitedReservationsAdapter

        return view
    }

    private fun getMyOffers() {
        offerController.getMyOffers { offers ->
            offers?.let {
                for (offer in it) {
                    offerSent.add(offer)
                }
            }
        }
    }

    private fun getMyReservation() {
        reservationController.getMyReservation { reservations ->
            reservations?.let {
                for (reservation in it) {
                    reservationSent.add(reservation)
                }
            }
        }
    }

}