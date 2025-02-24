package com.example.dietiestates25.view.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.controller.ReservationController
import com.example.dietiestates25.controller.ReservationViewModel
import com.example.dietiestates25.model.MeteoRequest
import com.example.dietiestates25.model.PropertyResponse
import com.example.dietiestates25.model.Reservation
import com.example.dietiestates25.model.ReservationState
import java.util.Calendar
import java.util.Locale

class Reservation1Fragment : Fragment() {
    private val propertyViewModel: PropertyViewModel by activityViewModels()
    private val reservationViewModel: ReservationViewModel by activityViewModels()
    private lateinit var reservationController: ReservationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservationController = ReservationController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation1, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateInputField: EditText = view.findViewById(R.id.date_input_field)
        val timeInputField: EditText = view.findViewById(R.id.time_picker_input_field)
        val continueButton = view.findViewById<LinearLayout>(R.id.continue_button)

        dateInputField.setOnClickListener {
            showDatePickerDialog(dateInputField)
        }

        timeInputField.setOnClickListener {
            showTimePickerDialog(timeInputField)
        }

        continueButton.setOnClickListener {
            val selectedDate = dateInputField.text.toString()
            val selectedTime = timeInputField.text.toString()
            val property = propertyViewModel.selectedProperty.value

            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                property?.let {
                    getMeteo(property, selectedDate, selectedTime)
                }
            }
        }

        return view
    }

    private fun showDatePickerDialog(dateInputField: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(Locale.getDefault(),"%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                dateInputField.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog(timeInputField: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                timeInputField.setText(formattedTime)
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun getMeteo(property: PropertyResponse, selectedDate: String, selectedTime: String) {
        val meteoRequest = MeteoRequest(property.latitude, property.longitude, selectedDate)

        reservationController.getMeteo(meteoRequest) { result ->
            result?.let {
                val temperatureMax = it["temperature_max"] as? Double ?: 0.0
                val temperatureMin = it["temperature_min"] as? Double ?: 0.0
                val averageTemperature = (temperatureMax + temperatureMin) / 2

                reservationViewModel.selectReservation(Reservation(selectedDate, selectedTime, ReservationState.IN_SOSPESO, property))
                goToReservation2Property(averageTemperature.toString())
            }
        }
    }

    private fun goToReservation2Property(averageTemp: String) {
        val fragment = Reservation2Fragment.newInstance(averageTemp)
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_layout_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

}