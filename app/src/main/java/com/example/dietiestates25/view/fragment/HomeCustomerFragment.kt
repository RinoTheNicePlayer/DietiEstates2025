package com.example.dietiestates25.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.HomeCustomerController
import com.example.dietiestates25.model.Address
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

/**
 * A simple [Fragment] subclass.
 * Use the [HomeCustomerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeCustomerFragment : Fragment() {
    private lateinit var homeCustomerController: HomeCustomerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeCustomerController = HomeCustomerController(requireContext())
        initPlace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_customer, container, false)
        setupAutoComplete(view)
        return view
    }

    private fun initPlace() {
        homeCustomerController.initPlace(this)
    }

    private fun setupAutoComplete(view: View) {
        // Initialize the AutocompleteSupportFragment.
        val fragmentButton = view.findViewById<LinearLayout>(R.id.search_button)
        val autocompleteFragment = AutocompleteSupportFragment()

        childFragmentManager.beginTransaction()
            .replace(fragmentButton.id, autocompleteFragment)
            .commit()

        setAutoCompleteLimit(autocompleteFragment)

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Get info about the selected place and search.
                searchProperty(takePlaceAddress(place))
            }

            override fun onError(status: Status) {
                Log.e("no indirizzo selezionato", "An error occurred: $status")
            }
        })
    }

    private fun setAutoCompleteLimit(autocompleteFragment: AutocompleteSupportFragment) {
        homeCustomerController.setLimitAutoComplete(autocompleteFragment)
    }

    private fun searchProperty(address: Address) {
        homeCustomerController.searchImmobileFromAddress(address)
    }

    private fun takePlaceAddress(place: Place): Address {
        val cityName = place.addressComponents?.asList()?.find { it.types.contains("locality") }?.name ?: ""
        val streetName = place.addressComponents?.asList()?.find { it.types.contains("route") }?.name ?: ""
        val cap = place.addressComponents?.asList()?.find { it.types.contains("postal_code") }?.name ?: ""

        return Address(streetName, cityName, cap)
    }
}