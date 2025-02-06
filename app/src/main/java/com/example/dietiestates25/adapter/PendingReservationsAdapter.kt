package com.example.dietiestates25.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.model.Reservation

class PendingReservationsAdapter(
    private val reservations: List<Reservation>,
    private val onAcceptClick: (Reservation) -> Unit,
    private val onRejectClick: (Reservation) -> Unit
) : RecyclerView.Adapter<PendingReservationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.pending_reservation_property_name)
        val clientName: TextView = view.findViewById(R.id.pending_reservation_client_name)
        val date: TextView = view.findViewById(R.id.pending_reservation_date)
        val time: TextView = view.findViewById(R.id.pending_reservation_time)
        val acceptButton: ImageView = view.findViewById(R.id.pending_reservation_accept)
        val rejectButton: ImageView = view.findViewById(R.id.pending_reservation_reject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_pending_reservation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.propertyName.text = reservation.propertyName
        holder.clientName.text = reservation.clientName
        holder.date.text = reservation.date
        holder.time.text = reservation.time

        holder.acceptButton.setOnClickListener { onAcceptClick(reservation) }
        holder.rejectButton.setOnClickListener { onRejectClick(reservation) }
    }

    override fun getItemCount() = reservations.size
}