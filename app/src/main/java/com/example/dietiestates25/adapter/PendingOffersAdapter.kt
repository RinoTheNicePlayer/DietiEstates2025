package com.example.dietiestates25

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.model.Offer

class PendingOffersAdapter(private var offers: List<Offer>) :
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
        val offer = offers[position]
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

    override fun getItemCount() = offers.size

    fun updateList(newOffers: List<Offer>) {
        offers = newOffers
        notifyDataSetChanged()
    }
}