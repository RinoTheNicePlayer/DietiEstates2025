package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.dietiestates25.R

class HomeAgentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_agent, container, false)
        val createPropertyButton = view.findViewById<LinearLayout>(R.id.create_property_button)
        //TODO: gestisci sezione i miei immobili

        createPropertyButton.setOnClickListener {
            // go to create property
        }

        return view
    }
}