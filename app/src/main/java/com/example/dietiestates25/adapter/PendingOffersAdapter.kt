package com.example.dietiestates25.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.OfferController
import com.example.dietiestates25.model.OfferResponse
import com.example.dietiestates25.model.OfferState

class PendingOffersAdapter(private val context: Context, private var offerTests: List<OfferResponse>) :
    RecyclerView.Adapter<PendingOffersAdapter.OfferViewHolder>() {

    class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.pending_offer_property_name)
        val clientName: TextView = view.findViewById(R.id.pending_offer_client_name)
        val amount: TextView = view.findViewById(R.id.pending_offer_amount)
        val editButton: ImageView = view.findViewById(R.id.pending_offer_edit)
        val acceptButton: ImageView = view.findViewById(R.id.pending_offer_accept)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_pending_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offerTests[position]
        holder.propertyName.text = offer.property.address
        holder.clientName.text = offer.property.municipality
        holder.amount.text = holder.itemView.context.getString(R.string.offer_amount, offer.amount)

        val offerController = OfferController(context)

        // Eventuale gestione click sui bottoni
        holder.editButton.setOnClickListener {
            offer.state = OfferState.RIFIUTATA
            offerController.editOffer(offer) {
                offerTests = offerTests.filter { it.id != offer.id } // Rimuove l'offerta dalla lista
                notifyDataSetChanged()
            }
        }
        holder.acceptButton.setOnClickListener {
            offer.state = OfferState.ACCETTATA
            offerController.editOffer(offer) {
                offerTests = offerTests.filter { it.id != offer.id } // Rimuove l'offerta dalla lista
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = offerTests.size
}