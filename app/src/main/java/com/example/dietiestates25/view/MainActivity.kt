package com.example.dietiestates25.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.LoginController

class MainActivity : AppCompatActivity() {
    private lateinit var loginController: LoginController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginController = LoginController(this)

        val loginButton = findViewById<LinearLayout>(R.id.loginButtonContainer)
        val registerLink = findViewById<TextView>(R.id.registerTextView)

        loginButton.setOnClickListener{
            login()
        }

        registerLink.setOnClickListener {
            goToRegister()
        }
    }

    private fun login() {
        val email = findViewById<EditText>(R.id.emailHintTextView).text.toString()
        val password = findViewById<EditText>(R.id.passwordHintTextView).text.toString()
        val errorLabel = findViewById<TextView>(R.id.erroreLabel)

        if (loginController.areValid(email, password)){
            loginController.login(email, password)
            errorLabel.visibility = TextView.GONE
        }
        else {
            errorLabel.visibility = TextView.VISIBLE
        }
    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}