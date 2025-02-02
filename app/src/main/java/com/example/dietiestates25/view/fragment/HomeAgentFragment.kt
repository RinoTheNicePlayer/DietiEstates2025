package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.PropertyAdapter
import com.example.dietiestates25.model.Property

class HomeAgentFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyList = mutableListOf<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //TODO: gestisci sezione i miei immobili
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_agent, container, false)

        val createPropertyButton = view.findViewById<LinearLayout>(R.id.create_property_button)

        createPropertyButton.setOnClickListener {
            // go to create property
        }

        // Configura il RecyclerView per gli immobili dell'agente
        recyclerView = view.findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadAgentProperties()

        propertyAdapter = PropertyAdapter(propertyList)
        recyclerView.adapter = propertyAdapter

        return view
    }

    private fun loadAgentProperties() {
        propertyList.apply {
            add(Property("Appartamento 1", "€250,000", ""))
            add(Property("Villa 2", "€500,000", ""))
            add(Property("Loft 3", "€320,000", ""))
            add(Property("Monolocale 4", "€150,000", ""))
            add(Property("Attico 5", "€600,000", ""))
        }
    }
}