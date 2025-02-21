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
import com.example.dietiestates25.controller.HomeAgentController
import com.example.dietiestates25.model.PropertyResponse

class HomeAgentFragment : Fragment() {
    private lateinit var homeAgentController: HomeAgentController
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyList = mutableListOf<PropertyResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeAgentController = HomeAgentController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_agent, container, false)

        // Configura il RecyclerView per gli immobili dell'agente
        recyclerView = view.findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadAgentProperties()

        propertyAdapter = PropertyAdapter(requireContext(), propertyList)
        recyclerView.adapter = propertyAdapter

        val createPropertyButton = view.findViewById<LinearLayout>(R.id.create_property_button)

        createPropertyButton.setOnClickListener {
            navigateTo(CreatePropertyFragment())
        }

        return view
    }

    private fun loadAgentProperties() {
        homeAgentController.getMyProperties { properties ->
            if (properties != null) {
                for (property in properties) {
                    propertyList.add(property)
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container2, fragment)
            .addToBackStack(null)
            .commit()
    }
}