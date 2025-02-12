package com.example.dietiestates25.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.models.VisitedReservation

class VisitedReservationsAdapter(private var visitedReservations: List<VisitedReservation>) :
    RecyclerView.Adapter<VisitedReservationsAdapter.VisitedReservationsViewHolder>() {

    inner class VisitedReservationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyAddress: TextView = view.findViewById(R.id.reservation_property_address)
        val reservationState: TextView = view.findViewById(R.id.reservation_state)
        val date: TextView = view.findViewById(R.id.reservation_date)
        val time: TextView = view.findViewById(R.id.reservation_time)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VisitedReservationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_visited_reservation, parent, false)
        return VisitedReservationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VisitedReservationsViewHolder, position: Int) {
        val reservation = visitedReservations[position]
        holder.propertyAddress.text = reservation.propertyAddress
        holder.reservationState.text = reservation.reservationState
        holder.date.text = reservation.date
        holder.time.text = reservation.time

        when (reservation.reservationState) {
            "Accettata" -> holder.reservationState.setTextColor(Color.parseColor("#34C759"))
            "Rifiutata" -> holder.reservationState.setTextColor(Color.parseColor("#FF3B30"))
            "In sospeso" -> holder.reservationState.setTextColor(Color.parseColor("#FFCC00"))
            else -> holder.reservationState.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount(): Int = visitedReservations.size

    fun updateList(newList: List<VisitedReservation>) {
        visitedReservations = newList
        notifyDataSetChanged()
    }
}