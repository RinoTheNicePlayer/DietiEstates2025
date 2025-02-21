package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.PropertyAdapter
import com.example.dietiestates25.controller.PropertySearched
import com.example.dietiestates25.model.PropertyResponse

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyList = mutableListOf<PropertyResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val properties = PropertySearched.properties

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = view.findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadProperties(properties)

        propertyAdapter = PropertyAdapter(requireContext(), propertyList)
        recyclerView.adapter = propertyAdapter

        return view
    }

    private fun loadProperties(properties: List<PropertyResponse>?) {
        if (properties != null) {
            for (property in properties) {
                propertyList.add(property)
            }
        }
    }
}