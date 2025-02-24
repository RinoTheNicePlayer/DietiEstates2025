package com.example.dietiestates25.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.model.OfferResponse
import com.example.dietiestates25.model.OfferState

class SentOffersAdapter(private var sentOffers: List<OfferResponse>) :
    RecyclerView.Adapter<SentOffersAdapter.SentOffersViewHolder>() {

    inner class SentOffersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyAddress: TextView = view.findViewById(R.id.sent_offer_property_address)
        val offerState: TextView = view.findViewById(R.id.sent_offer_state)
        val offerAmount: TextView = view.findViewById(R.id.sent_offer_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentOffersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_sent_offer, parent, false)
        return SentOffersViewHolder(view)
    }

    override fun onBindViewHolder(holder: SentOffersViewHolder, position: Int) {
        val offer = sentOffers[position]
        holder.propertyAddress.text = offer.property.address
        holder.offerAmount.text = holder.itemView.context.getString(R.string.offer_amount, offer.amount)

        when (offer.state) {
            OfferState.ACCETTATA -> {
                holder.offerState.text = OfferState.ACCETTATA.stato
                holder.offerState.setTextColor(Color.parseColor("#34C759"))
            }
            OfferState.RIFIUTATA -> {
                holder.offerState.text = OfferState.RIFIUTATA.stato
                holder.offerState.setTextColor(Color.parseColor("#FF3B30"))
            }
            OfferState.IN_SOSPESO -> {
                holder.offerState.text = OfferState.IN_SOSPESO.stato
                holder.offerState.setTextColor(Color.parseColor("#FFCC00"))
            }
        }
    }

    override fun getItemCount(): Int = sentOffers.size
}