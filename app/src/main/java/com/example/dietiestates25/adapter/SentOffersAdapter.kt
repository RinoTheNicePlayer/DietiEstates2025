package com.example.dietiestates25.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.models.SentOffer

class SentOffersAdapter(private var sentOffers: List<SentOffer>) :
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
        holder.propertyAddress.text = offer.propertyAddress
        holder.offerAmount.text = "€ ${offer.amount}"

        when (offer.offerState) {
            "Accettata" -> {
                holder.offerState.text = "Accettata"
                holder.offerState.setTextColor(Color.parseColor("#34C759"))
            }
            "Rifiutata" -> {
                holder.offerState.text = "Rifiutata"
                holder.offerState.setTextColor(Color.parseColor("#FF3B30"))
            }
            "In sospeso" -> {
                holder.offerState.text = "In sospeso"
                holder.offerState.setTextColor(Color.parseColor("#FFCC00"))
            }
            else -> {
                holder.offerState.text = "Stato sconosciuto"
                holder.offerState.setTextColor(Color.GRAY)
            }
        }
    }

    override fun getItemCount(): Int = sentOffers.size

    fun updateList(newList: List<SentOffer>) {
        sentOffers = newList
        notifyDataSetChanged()
    }
}