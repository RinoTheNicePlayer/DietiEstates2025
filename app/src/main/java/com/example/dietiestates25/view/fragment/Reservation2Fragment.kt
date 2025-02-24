package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.ReservationController
import com.example.dietiestates25.controller.ReservationViewModel
import com.example.dietiestates25.model.Reservation

class Reservation2Fragment : Fragment() {
    private val reservationViewModel: ReservationViewModel by activityViewModels()
    private var averageTemperature: String? = "15°"
    private lateinit var reservationController: ReservationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservationController = ReservationController()
        arguments?.let {
            averageTemperature = it.getString(ARG_AVERAGE_TEMP)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation2, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateValue = view.findViewById<TextView>(R.id.date_value)
        val timeValue = view.findViewById<TextView>(R.id.time_value)
        val weatherTemperature = view.findViewById<TextView>(R.id.weather_temperature)
        val confirmButton = view.findViewById<LinearLayout>(R.id.confirm_button)

        reservationViewModel.selectedReservation.observe(viewLifecycleOwner, { reservation ->
            dateValue.text = reservation.date
            timeValue.text = reservation.time
        })

        weatherTemperature.text = averageTemperature

        confirmButton.setOnClickListener {
            val reservation = reservationViewModel.selectedReservation.value
            reservation?.let {
                saveReservation(reservation)
            }
        }

        return view
    }

    private fun saveReservation(reservation: Reservation) {
        reservationController.saveReservation(reservation) {
            // go to confirmed
        }
    }

    private fun goToReservationSent() {
        val fragment = ReservationSent()
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_layout_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val ARG_AVERAGE_TEMP = "averageTemperature"

        @JvmStatic
        fun newInstance(averageTemperature: String) =
            Reservation2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_AVERAGE_TEMP, averageTemperature)
                }
            }
    }
}