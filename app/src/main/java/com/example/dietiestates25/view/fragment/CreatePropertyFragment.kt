package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R

class CreatePropertyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_property, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // set the menu a tendina
        val saleRentSpinner = view.findViewById<Spinner>(R.id.sale_rent_spinner)
        initSaleRentSpinner(saleRentSpinner)

        val balconySpinner = view.findViewById<Spinner>(R.id.balcony_spinner)
        initBalconySpinner(balconySpinner)

        val elevatorSpinner = view.findViewById<Spinner>(R.id.elevator_spinner)
        initElevatorSpinner(elevatorSpinner)


        /// TODO: da aggiungere
        val createPropertiesButton = view.findViewById<LinearLayout>(R.id.create_property_button)
        createPropertiesButton.setOnClickListener {
            if (optionAreSelected(saleRentSpinner, balconySpinner)) {

            }
        }

        return view
    }

    private fun initSaleRentSpinner(saleRentSpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sale_rent_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            saleRentSpinner.adapter = adapter
        }
        saleRentSpinner.setSelection(0, false)
    }

    private fun initBalconySpinner(balconySpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.balcony_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            balconySpinner.adapter = adapter
        }
        balconySpinner.setSelection(0, false)
    }

    private fun initElevatorSpinner(elevatorSpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.balcony_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            elevatorSpinner.adapter = adapter
        }
        elevatorSpinner.setSelection(0, false)
    }

    private fun optionAreSelected(saleRentSpinner: Spinner, balconySpinner: Spinner, elevatorSpinner: Spinner): Boolean {
        val selectedPosition1 = saleRentSpinner.selectedItemPosition
        val selectedPosition2 = balconySpinner.selectedItemPosition
        val selectedPosition3 = elevatorSpinner.selectedItemPosition

        if (selectedPosition1 == 0 || selectedPosition2 == 0 || selectedPosition3 == 0) {
            return false // label di errore
        } else {
            // val selectedOption = saleRentSpinner.selectedItem.toString()
            return true
        }
    }

}