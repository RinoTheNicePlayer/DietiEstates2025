
package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthManager
import com.example.dietiestates25.controller.OfferViewModel
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.controller.S3Controller
import com.example.dietiestates25.model.PropertyResponse

class OfferSentFragment : Fragment() {
    private var containerId: Int = 0
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private val offerViewModel: OfferViewModel by activityViewModels()
    private lateinit var s3Controller: S3Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        s3Controller = S3Controller(requireContext())
        arguments?.let {
            containerId = it.getInt(ARG_CONTAINER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offer_sent, container, false)

        val offerValue = view.findViewById<TextView>(R.id.offer_details_value)
        val propertyImage = view.findViewById<ImageView>(R.id.property_card_image)
        val propertyAddress = view.findViewById<TextView>(R.id.property_address_value)
        val propertyValue = view.findViewById<TextView>(R.id.property_previous_price_value)
        val goBackButton = view.findViewById<LinearLayout>(R.id.return_to_home_button)

        offerViewModel.selectedOffer.observe(viewLifecycleOwner) { offer ->
            offerValue.text = getString(R.string.offer_text, offer.price)
        }

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner) { property ->
            loadImage(property, propertyImage)
            propertyAddress.text = getString(R.string.property_address_text, property.address, property.municipality)
            propertyValue.text = getString(R.string.property_price_text, property.price)
        }

        goBackButton.setOnClickListener {
            goBackToHome()
        }

        return view
    }

    private fun goBackToHome() {
        // Clear the back stack
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragment: Fragment = if (AuthManager.role == "Cliente") {
            HomeCustomerFragment()
        } else {
            HomeAgentFragment()
        }

        parentFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun loadImage(property: PropertyResponse, imageProperty: ImageView) {
        s3Controller.getUrlFromStoragePath(property.pathImage) { url ->
            if (url != null) {
                imageProperty.load(url) {
                    crossfade(true) // Effetto di transizione
                    placeholder(R.drawable.image2) // Placeholder mentre carica
                    error(R.drawable.image2) // Immagine di errore se fallisce
                }
            } else {
                imageProperty.setImageResource(R.drawable.image2)
            }
        }
    }

    companion object {
        private const val ARG_CONTAINER_ID = "container_id"

        @JvmStatic
        fun newInstance(containerId: Int) =
            OfferSentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTAINER_ID, containerId)
                }
            }
    }
}