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
import com.example.dietiestates25.model.PropertyTest

class HomeAgentFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyTestList = mutableListOf<PropertyTest>()

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
            navigateTo(CreatePropertyFragment())
        }

        // Configura il RecyclerView per gli immobili dell'agente
        recyclerView = view.findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadAgentProperties()

        propertyAdapter = PropertyAdapter(propertyTestList)
        recyclerView.adapter = propertyAdapter

        return view
    }

    private fun loadAgentProperties() {
        propertyTestList.apply {
            add(PropertyTest("Appartamento 1", "€250,000", ""))
            add(PropertyTest("Villa 2", "€500,000", ""))
            add(PropertyTest("Loft 3", "€320,000", ""))
            add(PropertyTest("Monolocale 4", "€150,000", ""))
            add(PropertyTest("Attico 5", "€600,000", ""))
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container2, fragment)
            .addToBackStack(null)
            .commit()
    }
}