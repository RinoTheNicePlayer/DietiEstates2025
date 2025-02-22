package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.PropertyAdapter
import com.example.dietiestates25.controller.PropertySearched
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.model.PropertyResponse

class SearchFragment : Fragment(), PropertyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyList = mutableListOf<PropertyResponse>()
    private val propertyViewModel: PropertyViewModel by activityViewModels()

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

        propertyAdapter = PropertyAdapter(requireContext(), propertyList, this)
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

    override fun onItemClick(property: PropertyResponse) {
        propertyViewModel.selectProperty(property)
        goToPropertyDetails()
    }

    private fun goToPropertyDetails() {
        val fragment = PropertyDetailsFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}