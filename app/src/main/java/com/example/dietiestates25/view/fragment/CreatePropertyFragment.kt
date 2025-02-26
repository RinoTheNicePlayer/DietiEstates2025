package com.example.dietiestates25.view.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.PropertyController
import com.example.dietiestates25.controller.S3Controller
import com.example.dietiestates25.model.Property

class CreatePropertyFragment : Fragment() {
    private var selectedImageUri: Uri? = null
    private lateinit var s3Controller: S3Controller
    private lateinit var propertyController: PropertyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        s3Controller = S3Controller(requireContext())
        propertyController = PropertyController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_property, container, false)

        // Enable the up back button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // set the menu a tendina
        val saleRentSpinner = view.findViewById<Spinner>(R.id.sale_rent_spinner)
        initSaleRentSpinner(saleRentSpinner)

        val balconySpinner = view.findViewById<Spinner>(R.id.balcony_spinner)
        initBalconySpinner(balconySpinner)

        val elevatorSpinner = view.findViewById<Spinner>(R.id.elevator_spinner)
        initElevatorSpinner(elevatorSpinner)

        val addPropertyImage = view.findViewById<ImageView>(R.id.property_image)
        val createPropertiesButton = view.findViewById<LinearLayout>(R.id.create_property_button)
        val errorLabel = view.findViewById<TextView>(R.id.error_label_property)

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            selectedImageUri = uri
            addPropertyImage.setImageURI(uri)
        }

        addPropertyImage.setOnClickListener {
            loadImage.launch("image/*")
        }

        createPropertiesButton.setOnClickListener {
            val address = view.findViewById<EditText>(R.id.address_input_field).text.toString()
            val municipality = view.findViewById<EditText>(R.id.municipality_input_field).text.toString()
            val description = view.findViewById<EditText>(R.id.description_input_field).text.toString()
            val price = view.findViewById<EditText>(R.id.price_input_field).text.toString().toDouble()
            val nRooms = view.findViewById<EditText>(R.id.rooms_input_field).text.toString().toInt()
            val nBathrooms = view.findViewById<EditText>(R.id.bathrooms_input_field).text.toString().toInt()
            val floor = view.findViewById<EditText>(R.id.floors_input_field).text.toString().toInt()
            val size = view.findViewById<EditText>(R.id.size_input_field).text.toString().toInt()

            if (
                allFieldsAreValid(saleRentSpinner, balconySpinner, elevatorSpinner,
                    address, municipality, description, price, nRooms, nBathrooms, floor, size)
                ) {
                val property = Property(
                    description,
                    saveImageToS3(addPropertyImage),
                    price,
                    size,
                    nBathrooms,
                    nRooms,
                    saleRentSpinner.selectedItem.toString(),
                    address,
                    municipality,
                    floor,
                    elevatorSpinner.selectedItem.toString() == "Si",
                    balconySpinner.selectedItem.toString() == "Si"
                )

                propertyController.saveProperty(property) {
                    goBack()
                    errorLabel.visibility = View.INVISIBLE
                }
            }
            else {
                errorLabel.visibility = View.VISIBLE
            }
        }

        return view
    }

    private fun saveImageToS3(addPropertyImage: ImageView): String {
        val uniquePath = if (selectedImageUri != null) {
            s3Controller.uploadInputStream(selectedImageUri!!)
        } else {
            s3Controller.uploadDefaultImage(addPropertyImage)
        }

        return uniquePath
    }

    private fun initSaleRentSpinner(saleRentSpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sale_rent_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            saleRentSpinner.adapter = adapter
        }
        saleRentSpinner.setSelection(0, false)
    }

    private fun initBalconySpinner(balconySpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.balcony_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            balconySpinner.adapter = adapter
        }
        balconySpinner.setSelection(0, false)
    }

    private fun initElevatorSpinner(elevatorSpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.balcony_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            elevatorSpinner.adapter = adapter
        }
        elevatorSpinner.setSelection(0, false)
    }

    private fun allFieldsAreValid(
        saleRentSpinner: Spinner,
        balconySpinner: Spinner,
        elevatorSpinner: Spinner,
        address: String,
        city: String,
        description: String,
        price: Double,
        nRooms: Int,
        nBathrooms: Int,
        floor: Int,
        size: Int
    ): Boolean {
        return optionsAreSelected(saleRentSpinner, balconySpinner, elevatorSpinner)
                && areValid(address, city, description, price, nRooms, nBathrooms, floor, size)
    }

    private fun areValid(address: String, city: String, description: String, price: Double, nRooms: Int, nBathrooms: Int, floor: Int, size: Int): Boolean {
        return address.isNotEmpty() && city.isNotEmpty() && description.isNotEmpty() && price > 0 && nRooms > 0 && nBathrooms > 0 && floor > 0 && size > 0
    }

    private fun optionsAreSelected(saleRentSpinner: Spinner, balconySpinner: Spinner, elevatorSpinner: Spinner): Boolean {
        val selectedPosition1 = saleRentSpinner.selectedItemPosition
        val selectedPosition2 = balconySpinner.selectedItemPosition
        val selectedPosition3 = elevatorSpinner.selectedItemPosition

        return selectedPosition1 == 0 || selectedPosition2 == 0 || selectedPosition3 == 0
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

}