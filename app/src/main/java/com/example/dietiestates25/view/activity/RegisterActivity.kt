package com.example.dietiestates25.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthController

class RegisterActivity : AppCompatActivity() {
    private lateinit var controller: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        controller = AuthController(this)


        val registerButton = findViewById<LinearLayout>(R.id.register_button)

        registerButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val email = findViewById<EditText>(R.id.email_hint).text.toString()
        val password = findViewById<EditText>(R.id.password_hint).text.toString()
        val errorLabel = findViewById<TextView>(R.id.errorLabel)

        if (controller.areValid(email, password)) {
            controller.signUpWithAmplify(email, password, "Cliente", errorLabel, this)
        }
        else {
            errorLabel.visibility = TextView.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}