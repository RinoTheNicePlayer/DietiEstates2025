package com.example.dietiestates25.view

import android.os.Bundle
import android.widget.EditText
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
            registrazione()
        }
    }

    private fun registrazione(){
        val email = findViewById<EditText>(R.id.emailHintTextView).text.toString()
        val password = findViewById<EditText>(R.id.passwordHintTextView).text.toString()
        val erroreLabel = findViewById<TextView>(R.id.errore)

        if (controller.areValid(email, password)) {
            controller.registerUser(email, password)
            erroreLabel.visibility = TextView.GONE
        }
        else {
            erroreLabel.visibility = TextView.VISIBLE
        }
    }
}