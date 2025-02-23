package com.example.dietiestates25.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.callback.NavigationCallback
import com.example.dietiestates25.controller.OfferController
import com.example.dietiestates25.controller.OfferViewModel
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.model.Offer
import com.example.dietiestates25.model.OfferState
import com.example.dietiestates25.model.PropertyResponse

class OfferFragment : Fragment() {
    private var containerId: Int = 0
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private val offerViewModel: OfferViewModel by activityViewModels()
    private lateinit var offerController: OfferController
    private var navigationCallback: NavigationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offerController = OfferController(requireContext())
        arguments?.let {
            containerId = it.getInt(ARG_CONTAINER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offer, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageProperty = view.findViewById<ImageView>(R.id.property_image)
        val address = view.findViewById<TextView>(R.id.property_address)
        val price = view.findViewById<TextView>(R.id.property_price)
        val offer = view.findViewById<EditText>(R.id.offer_input_field)
        val offerButton = view.findViewById<LinearLayout>(R.id.send_button)

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner) { property ->
            loadImage(property, imageProperty)
            address.text = getString(R.string.property_address, property.address, property.municipality)
            price.text = getString(R.string.property_price, property.price)
        }

        offerButton.setOnClickListener {
            val offerValue = offer.text.toString().toDoubleOrNull()
            val property = propertyViewModel.selectedProperty.value
            if (offerValue != null && property != null) {
                saveOffer(offerValue, property)
            }
        }

        return view
    }

    private fun loadImage(property: PropertyResponse, imageProperty: ImageView) {
        offerController.loadImage(property, imageProperty)
    }

    private fun saveOffer(price: Double, property: PropertyResponse) {
        val offer = Offer(price, OfferState.IN_SOSPESO, property)
        offerController.sendOffer(offer) {
            val fragment = OfferSentFragment.newInstance(containerId)
            offerViewModel.selectOffer(offer)
            navigationCallback?.navigateTo(fragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationCallback = if (context is NavigationCallback) {
            context
        } else if (parentFragment is NavigationCallback) {
            parentFragment as NavigationCallback
        } else {
            throw RuntimeException("$context must implement NavigationCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationCallback = null
    }

    companion object {
        private const val ARG_CONTAINER_ID = "container_id"

        @JvmStatic
        fun newInstance(containerId: Int) =
            OfferFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTAINER_ID, containerId)
                }
            }
    }

}