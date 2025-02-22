package com.example.dietiestates25.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietiestates25.model.PropertyResponse

class PropertyViewModel : ViewModel() {
    private val _selectedProperty = MutableLiveData<PropertyResponse>()
    val selectedProperty: LiveData<PropertyResponse> get() = _selectedProperty

    fun selectProperty(property: PropertyResponse) {
        _selectedProperty.value = property
    }
}