package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.ConfirmedOffersAdapter
import com.example.dietiestates25.adapter.PendingOffersAdapter
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.OfferController
import com.example.dietiestates25.model.OfferResponse
import com.example.dietiestates25.model.OfferState

class OfferAgentFragment : Fragment() {
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private var offerConfirmed = mutableListOf<OfferResponse>()
    private var offerPending = mutableListOf<OfferResponse>()
    private lateinit var pendingOffersAdapter: PendingOffersAdapter
    private lateinit var confirmedOffersAdapter: ConfirmedOffersAdapter
    private lateinit var offerController: OfferController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offerController = OfferController(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offer_agent, container, false)

        // trova i recycler
        recyclerView1 = view.findViewById(R.id.recycler_pending_offers)
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView2 = view.findViewById(R.id.recycler_confirmed_offers)
        recyclerView2.layoutManager = LinearLayoutManager(context)

        //builda gli adapter
        loadOffers()

        // assegna adapter
        pendingOffersAdapter = PendingOffersAdapter(requireContext(), offerPending)
        recyclerView1.adapter = pendingOffersAdapter
        confirmedOffersAdapter = ConfirmedOffersAdapter(offerConfirmed)
        recyclerView2.adapter = confirmedOffersAdapter

        return view
    }

    private fun loadOffers() {
        offerController.getOffersFromAgency { offers ->
            offers?.let { offerResponses ->
                offerResponses.forEach {
                    if (it.state == OfferState.ACCETTATA) {
                        offerConfirmed.add(it)
                    } else if (it.state == OfferState.IN_SOSPESO){
                        offerPending.add(it)
                    }
                }
            }
        }
    }
}