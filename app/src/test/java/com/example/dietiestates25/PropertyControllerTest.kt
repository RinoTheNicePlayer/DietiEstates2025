package com.example.dietiestates25

import org.junit.Assert.*
import com.example.dietiestates25.controller.PropertyController
import org.junit.Before
import org.junit.Test

class PropertyControllerTest {
    private lateinit var propertyController: PropertyController

    @Before
    fun setUp() {
        propertyController = PropertyController()
    }

    @Test
    fun testValidValues() {
        val address = "Afragola"
        val type = "In Affitto"
        val priceMin = 400.0
        val priceMax = 900.0
        val size = 20
        val nBathrooms = 2

        assertTrue(propertyController.validateSearchParameters(address, type, priceMin, priceMax, size, nBathrooms))
    }

    @Test
    fun testNullValues() {
        val address = "Afragola"
        val type = null
        val priceMin = null
        val priceMax = null
        val size = null
        val nBathrooms = null

        assertTrue(propertyController.validateSearchParameters(address, type, priceMin, priceMax, size, nBathrooms))
    }

    @Test
    fun testNotValidValues() {
        val address = ""
        val type = ""
        val priceMin = -400.0
        val priceMax = -900.0
        val size = -20
        val nBathrooms = -2

        assertFalse(propertyController.validateSearchParameters(address, type, priceMin, priceMax, size, nBathrooms))
    }
}