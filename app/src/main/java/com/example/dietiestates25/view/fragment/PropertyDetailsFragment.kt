package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner, Observer{ property ->
            // Aggiorna la UI con i dettagli dell'immobile
        })

        return view
    }

}