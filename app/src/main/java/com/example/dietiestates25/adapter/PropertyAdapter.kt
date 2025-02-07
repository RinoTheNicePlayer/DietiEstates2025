package com.example.dietiestates25.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dietiestates25.R
import com.example.dietiestates25.model.PropertyTest

class PropertyAdapter(private val propertyTestList: List<PropertyTest>) :
    RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = propertyTestList[position]
        holder.propertyName.text = property.name
        holder.propertyPrice.text = property.price

        // Carica l'immagine con Coil
        holder.propertyImage.load(property.imageUrl) {
            crossfade(true) // Effetto di transizione
            placeholder(R.drawable.image2) // Placeholder mentre carica
            error(R.drawable.image2) // Immagine di errore se fallisce
        }
    }

    override fun getItemCount(): Int = propertyTestList.size

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val propertyName: TextView = itemView.findViewById(R.id.property_name)
        val propertyPrice: TextView = itemView.findViewById(R.id.property_price)
        val propertyImage: ImageView = itemView.findViewById(R.id.property_image)
    }
}