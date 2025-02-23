package com.example.dietiestates25.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.model.OfferTest

class PendingOffersAdapter(private var offerTests: List<OfferTest>) :
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
        holder.propertyName.text = offer.propertyName
        holder.clientName.text = offer.clientName
        holder.amount.text = "€ ${offer.amount}"

        // Eventuale gestione click sui bottoni
        holder.editButton.setOnClickListener {
            // Implementare azione per modificare l'offerta
        }
        holder.acceptButton.setOnClickListener {
            // Implementare azione per accettare l'offerta
        }
    }

    override fun getItemCount() = offerTests.size

    fun updateList(newOfferTests: List<OfferTest>) {
        offerTests = newOfferTests
        notifyDataSetChanged()
    }
}