package com.example.dietiestates25.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthController


class MainActivity : AppCompatActivity() {
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authController = AuthController(this)

        val loginButton = findViewById<LinearLayout>(R.id.loginButtonContainer)
        val registerLink = findViewById<TextView>(R.id.registerTextView)
        val externalLogin = findViewById<LinearLayout>(R.id.social_login_container)

        val errorLabel = findViewById<TextView>(R.id.erroreLabel)

        loginButton.setOnClickListener{
            login(errorLabel)
        }

        registerLink.setOnClickListener {
            goToRegister()
        }

        externalLogin.setOnClickListener {
            loginExternal()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        authController.handleRedirection(intent, this)
    }

    private fun login(errorLabel: TextView) {
        val email = findViewById<EditText>(R.id.email_hint).text.toString()
        val password = findViewById<EditText>(R.id.passwordHintTextView).text.toString()

        if (authController.areValid(email, password)){
            authController.loginWithAmplify(email, password, errorLabel, this)
        }
        else {
            errorLabel.visibility = TextView.VISIBLE
        }
    }

    private fun loginExternal() {
        authController.loginWithThirdProviders(this)
    }

    private fun goToRegister() {
        authController.navigateTo(this, RegisterActivity())
    }
}