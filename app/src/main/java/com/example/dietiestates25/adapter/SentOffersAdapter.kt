package com.example.dietiestates25.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.models.SentOffer

class SentOffersAdapter(private var sentOffers: List<SentOffer>) :
    RecyclerView.Adapter<SentOffersAdapter.SentOffersViewHolder>() {

    inner class SentOffersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.sent_offer_property_name)
        val clientName: TextView = view.findViewById(R.id.sent_offer_name)
        val offerAmount: TextView = view.findViewById(R.id.sent_offer_amount)
        val offerIcon: ImageView = view.findViewById(R.id.sent_offer_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentOffersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_sent_offer, parent, false)
        return SentOffersViewHolder(view)
    }

    override fun onBindViewHolder(holder: SentOffersViewHolder, position: Int) {
        val offer = sentOffers[position]
        holder.propertyName.text = offer.propertyName
        holder.clientName.text = offer.clientName
        holder.offerAmount.text = "€ ${offer.amount}"
    }

    override fun getItemCount(): Int = sentOffers.size

    fun updateList(newList: List<SentOffer>) {
        sentOffers = newList
        notifyDataSetChanged()
    }
}