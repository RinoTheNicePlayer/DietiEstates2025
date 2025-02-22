package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.controller.PropertyDetailsController
import com.example.dietiestates25.model.InterestingPoints
import com.example.dietiestates25.model.PropertyResponse
import java.util.Locale

class PropertyDetailsFragment : Fragment() {
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private lateinit var propertyDetailsController: PropertyDetailsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        propertyDetailsController = PropertyDetailsController(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_details, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner) { property ->
            getInterestingPoints(property, interestingPoint)
            loadImage(property, imageProperty)
            saleRent.text = property.type
            address.text = property.address
            municipality.text = property.municipality
            price.text = getString(R.string.price_format, property.price)
            rooms.text = String.format(Locale.getDefault(), "%,d", property.nRooms)
            size.text = getString(R.string.size_format, property.size)
            bathrooms.text = String.format(Locale.getDefault(), "%,d", property.nBathrooms)
            floor.text = String.format(Locale.getDefault(), "%,d", property.floor)
            elevator.text = if (property.hasElevator) "Sì" else "No"
            balcony.text = if (property.hasBalcony) "Sì" else "No"
            description.text = property.description
        }

        offerButton.setOnClickListener {

        }

        reservationButton.setOnClickListener {

        }

        return view
    }

    private fun getInterestingPoints(property: PropertyResponse, interestingPoint: TextView) {
        val interestingPoints = InterestingPoints(property.latitude, property.longitude)
        propertyDetailsController.getInterestingPoints(interestingPoints) { result ->
            handleSetTextInterestingPoints(result, interestingPoint)
        }
    }

    private fun handleSetTextInterestingPoints(result: Map<String, Int>?, interestingPoint: TextView) {
        result?.let {
            val scuolaCount = it["scuola"] ?: 0
            val parcoCount = it["parco"] ?: 0
            val trasportoCount = it["trasporto"] ?: 0

            val messages = mutableListOf<String>()

            if (scuolaCount > 0) {
                messages.add("vicino a ${if (scuolaCount > 1) "$scuolaCount scuole" else "una scuola"}")
            }
            if (parcoCount > 0) {
                messages.add("vicino a ${if (parcoCount > 1) "$parcoCount parchi" else "un parco"}")
            }
            if (trasportoCount > 0) {
                messages.add("vicino a ${if (trasportoCount > 1) "$trasportoCount fermate" else "una fermata"}")
            }

            interestingPoint.text = if (messages.isNotEmpty()) {
                messages.joinToString(" e ")
            } else {
                "Nessun punto di interesse vicino"
            }
        }
    }

    private fun loadImage(property: PropertyResponse, imageProperty: ImageView) {
        propertyDetailsController.loadImage(property, imageProperty)
    }

}