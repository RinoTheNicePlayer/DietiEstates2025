package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.PropertyViewModel
import androidx.lifecycle.Observer

class PropertyDetailsFragment : Fragment() {
    private val propertyViewModel: PropertyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_details, container, false)
        /// TODO: chiamata server per punti di interesse
        val interestingPoint = view.findViewById<TextView>(R.id.location_value)
        val imageProperty = view.findViewById<ImageView>(R.id.property_image)
        val saleRent = view.findViewById<TextView>(R.id.sale_rent_value)
        val address = view.findViewById<TextView>(R.id.address_value)
        val municipality = view.findViewById<TextView>(R.id.municipality_value)
        val price = view.findViewById<TextView>(R.id.price_value)
        val rooms = view.findViewById<TextView>(R.id.rooms_value)
        val size = view.findViewById<TextView>(R.id.size_value) // metri quadri
        val bathrooms = view.findViewById<TextView>(R.id.bathrooms_value)
        val floor = view.findViewById<TextView>(R.id.floor_value)
        val elevator = view.findViewById<TextView>(R.id.elevator_value)
        val balcony = view.findViewById<TextView>(R.id.balcony_value)
        val description = view.findViewById<TextView>(R.id.description_value)
        val offerButton = view.findViewById<LinearLayout>(R.id.offer_button)
        val reservationButton = view.findViewById<LinearLayout>(R.id.reservation_button)

        /// TODO: mappa

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner, Observer{ property ->
            // Aggiorna la UI con i dettagli dell'immobile
        })

        offerButton.setOnClickListener {

        }

        reservationButton.setOnClickListener {

        }

        return view
    }

}