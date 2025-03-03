package com.example.dietiestates25.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.callback.NavigationCallback
import com.example.dietiestates25.controller.AuthManager
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.controller.PropertyDetailsController
import com.example.dietiestates25.model.InterestingPoints
import com.example.dietiestates25.model.PropertyResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class PropertyDetailsFragment : Fragment(), OnMapReadyCallback {
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private lateinit var propertyDetailsController: PropertyDetailsController
    private var navigationCallback: NavigationCallback? = null
    private var containerId: Int = 0
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        propertyDetailsController = PropertyDetailsController(requireContext())
        arguments?.let {
            containerId = it.getInt(ARG_CONTAINER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_details, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val interestingPoint = view.findViewById<TextView>(R.id.location_value)
        val imageProperty = view.findViewById<ImageView>(R.id.property_image)
        val saleRent = view.findViewById<TextView>(R.id.sale_rent_value)
        val address = view.findViewById<TextView>(R.id.address_value)
        val municipality = view.findViewById<TextView>(R.id.municipality_value)
        val price = view.findViewById<TextView>(R.id.price_value)
        val rooms = view.findViewById<TextView>(R.id.rooms_value)
        val size = view.findViewById<TextView>(R.id.size_value) // metri quadri
        val bathrooms = view.findViewById<TextView>(R.id.bathrooms_value)
        val floor = view.findViewById<TextView>(R.id.floor_value)
        val elevator = view.findViewById<TextView>(R.id.elevator_value)
        val balcony = view.findViewById<TextView>(R.id.balcony_value)
        val description = view.findViewById<TextView>(R.id.description_value)
        val offerButton = view.findViewById<LinearLayout>(R.id.offer_button)
        val reservationButton = view.findViewById<LinearLayout>(R.id.reservation_button)

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner) { property ->
            getInterestingPoints(property, interestingPoint)
            loadImage(property, imageProperty)
            saleRent.text = property.type
            address.text = property.address
            municipality.text = property.municipality
            price.text = getString(R.string.price_format, property.price)
            rooms.text = String.format(Locale.getDefault(), "%,d", property.nRooms)
            size.text = getString(R.string.size_format, property.size)
            bathrooms.text = String.format(Locale.getDefault(), "%,d", property.nBathrooms)
            floor.text = String.format(Locale.getDefault(), "%,d", property.floor)
            elevator.text = if (property.hasElevator) "Sì" else "No"
            balcony.text = if (property.hasBalcony) "Sì" else "No"
            description.text = property.description
        }

        offerButton.setOnClickListener {
            val fragment = OfferFragment.newInstance(containerId)
            navigationCallback?.navigateTo(fragment)
        }

        reservationButton.setOnClickListener {
            if (AuthManager.role == "Cliente") {
                navigationCallback?.navigateTo(Reservation1Fragment())
            }
        }

        return view
    }

    private fun getInterestingPoints(property: PropertyResponse, interestingPoint: TextView) {
        val interestingPoints = InterestingPoints(property.latitude, property.longitude)
        propertyDetailsController.getInterestingPoints(interestingPoints, interestingPoint)
    }

    private fun loadImage(property: PropertyResponse, imageProperty: ImageView) {
        propertyDetailsController.loadImage(property, imageProperty)
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
            PropertyDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTAINER_ID, containerId)
                }
            }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        propertyViewModel.selectedProperty.observe(viewLifecycleOwner) { property ->
            val propertyLocation = LatLng(property.latitude, property.longitude)
            googleMap.addMarker(
                MarkerOptions().position(propertyLocation).title(property.address)
            )
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(propertyLocation, 15f)
            )
        }
    }

}