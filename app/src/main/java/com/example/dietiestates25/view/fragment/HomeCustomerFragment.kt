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
import com.example.dietiestates25.controller.PropertySearched

class HomeCustomerFragment : Fragment() {
    private lateinit var homeCustomerController: HomeCustomerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeCustomerController = HomeCustomerController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_customer, container, false)

        val searchButton = view.findViewById<LinearLayout>(R.id.search_button)

        searchButton.setOnClickListener {
            val bottomSheet = AddressBottomSheetFragment { address, callback ->
                sendAddressToBackend(address) { isValid ->
                    callback(isValid)  // Aggiorna la UI della bottom sheet
                }
            }

            bottomSheet.show(parentFragmentManager, "AddressBottomSheet")
        }

        return view
    }

    private fun sendAddressToBackend(address: String, callback: (Boolean) -> Unit) {
        Log.d("HomeCustomerFragment", "Sending address to backend: $address")
        homeCustomerController.searchPropertyFromAddress(address) { properties ->
            if (properties != null) {
                PropertySearched.properties = properties
                goToSearchProperty()
                callback(true)
            }
            else {
                callback(false)
            }
        }
    }

    private fun goToSearchProperty() {
        val searchFragment = SearchFragment()
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_layout_container, searchFragment)
            addToBackStack(null)
            commit()
        }
    }

}