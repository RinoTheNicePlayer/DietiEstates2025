package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.PropertyController
import com.example.dietiestates25.controller.PropertySearched

class FilterFragment : Fragment() {
    private lateinit var propertyController: PropertyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        propertyController = PropertyController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filter, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val saleRentSpinner = view.findViewById<Spinner>(R.id.sale_rent_spinner)
        val priceMin = view.findViewById<EditText>(R.id.price_min_label)
        val priceMax = view.findViewById<EditText>(R.id.price_max_label)
        val size = view.findViewById<EditText>(R.id.size_label)
        val nBathrooms = view.findViewById<EditText>(R.id.bathrooms_input_field)
        val searchButton = view.findViewById<LinearLayout>(R.id.search_button)

        searchButton.setOnClickListener {
            applyFiltersToProperties(saleRentSpinner, priceMin, priceMax, size, nBathrooms)
        }

        return view
    }

    private fun applyFiltersToProperties(
        saleRentSpinner: Spinner,
        priceMin: EditText,
        priceMax: EditText,
        size: EditText,
        nBathrooms: EditText
    ) {
        val address = PropertySearched.address!!
        val type = saleRentSpinner.selectedItem.toString()
        val priceMinValue = priceMin.text.toString().toDoubleOrNull()
        val priceMaxValue = priceMax.text.toString().toDoubleOrNull()
        val sizeValue = size.text.toString().toIntOrNull()
        val nBathroomsValue = nBathrooms.text.toString().toIntOrNull()

        propertyController.searchPropertyFromAddress(
            address,
            type,
            priceMinValue,
            priceMaxValue,
            sizeValue,
            nBathroomsValue,
            callback = { properties ->
                PropertySearched.properties = properties
                goBack()
            }
        )
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

}