package com.example.dietiestates25.view

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.RegisterController

class RegisterActivity : AppCompatActivity() {
    private lateinit var controller: RegisterController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        controller = RegisterController(this)


        val registerButton = findViewById<LinearLayout>(R.id.createButtonContainer)

        registerButton.setOnClickListener {
            registrazione()
        }
    }

    private fun registrazione(){
        val email = findViewById<EditText>(R.id.emailHintTextView).text.toString()
        val password = findViewById<EditText>(R.id.passwordHintTextView).text.toString()
        val errorLabel = findViewById<TextView>(R.id.errore)

        if (controller.areValid(email, password)) {
            controller.registerUser(email, password)
            errorLabel.visibility = TextView.GONE
        }
        else {
            errorLabel.visibility = TextView.VISIBLE
        }
    }
}