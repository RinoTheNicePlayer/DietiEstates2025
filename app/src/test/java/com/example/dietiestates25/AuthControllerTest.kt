package com.example.dietiestates25

import org.junit.Assert.*
import com.example.dietiestates25.controller.AuthController
import org.junit.Before
import org.junit.Test

class AuthControllerTest {
    private lateinit var authController: AuthController

    @Before
    fun setUp() {
        authController = AuthController()
    }

    @Test
    fun testCorrectValues() {
        val email = "test@example.com"
        val password = "password123"

        assertTrue(authController.areValid(email, password))
    }

    @Test
    fun testNotCorrectValues() {
        val email = "alessandro"
        val password = "lu"

        assertFalse(authController.areValid(email, password))
    }

    @Test
    fun testEmptyValues() {
        val email = ""
        val password = ""

        assertFalse(authController.areValid(email, password))
    }
}