package com.example.dietiestates25.view

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.RegistrazioneController

class RegistrazioneActivity : AppCompatActivity() {
    private lateinit var controller: RegistrazioneController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        controller = RegistrazioneController(this)


        val registrazioneButton = findViewById<LinearLayout>(R.id.createButtonContainer)
        registrazioneButton.setOnClickListener {
            val email = findViewById<TextView>(R.id.emailHintTextView).text.toString()
            val password = findViewById<TextView>(R.id.passwordHintTextView).text.toString()

            if (controller.areValid(email, password)) {
                controller.registerUser(email, password)
            }
            else {
                // Mostra una label di errore
            }
        }
    }
}