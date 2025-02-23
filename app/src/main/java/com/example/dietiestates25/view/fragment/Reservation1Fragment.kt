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
import com.example.dietiestates25.model.MeteoRequest
import java.util.Calendar
import java.util.Locale

class Reservation1Fragment : Fragment() {
    private val propertyViewModel: PropertyViewModel by activityViewModels()
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
                    getMeteo(it.latitude, it.longitude, selectedDate)
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

    private fun getMeteo(latitude: Double, longitude: Double, selectedDate: String) {
        val meteoRequest = MeteoRequest(latitude, longitude, selectedDate)

        reservationController.getMeteo(meteoRequest) { result ->
            // go to reservation2 fragment
        }
    }

}