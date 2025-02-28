package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.PropertyAdapter
import com.example.dietiestates25.callback.NavigationCallback
import com.example.dietiestates25.controller.PropertyController
import com.example.dietiestates25.controller.PropertySearched
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.model.PropertyResponse
import java.util.Locale

class SearchFragment : Fragment(), PropertyAdapter.OnItemClickListener, NavigationCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyList = mutableListOf<PropertyResponse>()
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private lateinit var propertyController: PropertyController
    private var isLoading = false
    private var currentPage = 0
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        propertyController = PropertyController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val previousPageIcon = view.findViewById<ImageView>(R.id.previous_page_icon)
        val nextPageIcon = view.findViewById<ImageView>(R.id.next_page_icon)
        val currentPageText = view.findViewById<TextView>(R.id.current_page_text)
        val filterButton = view.findViewById<TextView>(R.id.filter_button)
        val properties = PropertySearched.properties
        recyclerView = view.findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadProperties(properties)

        propertyAdapter = PropertyAdapter(requireContext(), propertyList, this)
        recyclerView.adapter = propertyAdapter

        previousPageIcon.setOnClickListener {
            loadPreviousProperties(currentPageText)
        }

        nextPageIcon.setOnClickListener {
            loadNextProperties(currentPageText)
        }

        filterButton.setOnClickListener {
            navigateTo(FilterFragment())
        }

        updatePaginationButtons(previousPageIcon, nextPageIcon)

        return view
    }

    private fun loadNextProperties(currentPageText: TextView) {
        // nel setText +1 perche l'indice di partenza non è 0 come per currentPage
        if (currentPage < totalPages - 1) {
            currentPage++
            currentPageText.text = String.format(Locale.getDefault(), "%d", currentPage + 1)
            loadMoreProperties(currentPage)
        }
    }

    private fun loadPreviousProperties(currentPageText: TextView) {
        // nel setText +1 perche l'indice di partenza non è 0 come per currentPage
        if (currentPage > 0) {
            currentPage--
            currentPageText.text = String.format(Locale.getDefault(), "%d", currentPage + 1)
            loadMoreProperties(currentPage)
        }
    }

    private fun loadProperties(properties: List<PropertyResponse>?) {
        if (properties != null) {
            for (property in properties) {
                propertyList.add(property)
            }
        }
    }

    private fun loadMoreProperties(page: Int) {
        isLoading = true
        propertyController.searchPropertyFromAddress(
            PropertySearched.address!!,
            page = page,
            callback = { newProperties ->
                newProperties?.let {
                    propertyList.clear()
                    propertyList.addAll(it)
                    propertyAdapter.notifyDataSetChanged()
                    totalPages = calculateTotalPages(it.size)
                }
                isLoading = false
                updatePaginationButtons(view?.findViewById(R.id.previous_page_icon), view?.findViewById(R.id.next_page_icon))
            }
        )
    }

    private fun calculateTotalPages(totalProperties: Int): Int {
        val pageSize = 5
        return (totalProperties + pageSize - 1) / pageSize
    }

    private fun updatePaginationButtons(previousPageIcon: ImageView?, nextPageIcon: ImageView?) {
        previousPageIcon?.isEnabled = currentPage > 0
        nextPageIcon?.isEnabled = currentPage < totalPages - 1
    }

    override fun onItemClick(property: PropertyResponse) {
        propertyViewModel.selectProperty(property)
        val fragment = PropertyDetailsFragment.newInstance(R.id.fragment_layout_container)
        navigateTo(fragment)
    }

    override fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}