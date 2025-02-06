package com.example.dietiestates25.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.model.Reservation

class ConfirmedReservationsAdapter(
    private val reservations: List<Reservation>
) : RecyclerView.Adapter<ConfirmedReservationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.confirmed_reservation_property_name)
        val clientName: TextView = view.findViewById(R.id.confirmed_reservation_client_name)
        val date: TextView = view.findViewById(R.id.confirmed_reservation_date)
        val time: TextView = view.findViewById(R.id.confirmed_reservation_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_confirmed_reservation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.propertyName.text = reservation.propertyName
        holder.clientName.text = reservation.clientName
        holder.date.text = reservation.date
        holder.time.text = reservation.time
    }

    override fun getItemCount() = reservations.size
}