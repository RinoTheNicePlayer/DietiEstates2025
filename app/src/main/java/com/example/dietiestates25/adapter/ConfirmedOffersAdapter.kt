package com.example.dietiestates25

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.model.Offer

class ConfirmedOffersAdapter(private var offers: List<Offer>) :
    RecyclerView.Adapter<ConfirmedOffersAdapter.OfferViewHolder>() {

    class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.confirmed_offer_property_name)
        val clientName: TextView = view.findViewById(R.id.confirmed_offer_client_name)
        val amount: TextView = view.findViewById(R.id.confirmed_offer_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_confirmed_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.propertyName.text = offer.propertyName
        holder.clientName.text = offer.clientName
        holder.amount.text = "€ ${offer.amount}"
    }

    override fun getItemCount() = offers.size

    fun updateList(newOffers: List<Offer>) {
        offers = newOffers
        notifyDataSetChanged()
    }
}