package com.example.dietiestates25.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.S3Controller
import com.example.dietiestates25.model.PropertyResponse

class PropertyAdapter(
    private val context: Context,
    private val propertyList: List<PropertyResponse>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(property: PropertyResponse)
    }

    private val s3Controller = S3Controller(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = propertyList[position]
        holder.propertyName.text = property.address
        holder.propertyPrice.text = holder.itemView.context.getString(R.string.price_format, property.price)

        // Carica l'immagine con Coil
        s3Controller.getUrlFromStoragePath(property.pathImage) { url ->
            if (url != null) {
                holder.propertyImage.load(url) {
                    crossfade(true) // Effetto di transizione
                    placeholder(R.drawable.image2) // Placeholder mentre carica
                    error(R.drawable.image2) // Immagine di errore se fallisce
                }
            }
            else {
                holder.propertyImage.setImageResource(R.drawable.image2)
            }
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(property)
        }
    }

    override fun getItemCount(): Int = propertyList.size

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val propertyName: TextView = itemView.findViewById(R.id.property_name)
        val propertyPrice: TextView = itemView.findViewById(R.id.property_price)
        val propertyImage: ImageView = itemView.findViewById(R.id.property_image)
    }
}