package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.controller.ReservationViewModel

class ReservationSentFragment : Fragment() {
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private val reservationViewModel: ReservationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_sent, container, false)

        val address = view.findViewById<TextView>(R.id.address_value)
        val date = view.findViewById<TextView>(R.id.date_value)
        val time = view.findViewById<TextView>(R.id.time_value)
        val returnToHomeButton = view.findViewById<TextView>(R.id.return_to_home_button)

        propertyViewModel.selectedProperty.observe(viewLifecycleOwner) { property ->
            address.text = property.address
        }

        reservationViewModel.selectedReservation.observe(viewLifecycleOwner) { reservation ->
            date.text = reservation.date
            time.text = reservation.time
        }

        returnToHomeButton.setOnClickListener {
            goBackToHomeCustomer()
        }

        return view
    }

    private fun goBackToHomeCustomer() {
        // Clear the back stack
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container, HomeCustomerFragment())
            .commit()
    }
}