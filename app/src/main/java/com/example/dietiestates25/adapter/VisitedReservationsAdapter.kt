package com.example.dietiestates25.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.models.VisitedReservation

class VisitedReservationsAdapter(private var visitedReservations: List<VisitedReservation>) :
    RecyclerView.Adapter<VisitedReservationsAdapter.VisitedReservationsViewHolder>() {

    inner class VisitedReservationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val propertyName: TextView = view.findViewById(R.id.reservation_property_name)
        val clientName: TextView = view.findViewById(R.id.reservation_client_name)
        val date: TextView = view.findViewById(R.id.reservation_date)
        val time: TextView = view.findViewById(R.id.reservation_time)
        val reservationIcon: ImageView = view.findViewById(R.id.reservation_icon)
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
        holder.propertyName.text = reservation.propertyName
        holder.clientName.text = reservation.clientName
        holder.date.text = reservation.date
        holder.time.text = reservation.time
    }

    override fun getItemCount(): Int = visitedReservations.size

    fun updateList(newList: List<VisitedReservation>) {
        visitedReservations = newList
        notifyDataSetChanged()
    }
}