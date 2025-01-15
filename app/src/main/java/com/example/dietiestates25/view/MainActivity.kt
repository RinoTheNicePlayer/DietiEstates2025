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
        loginController = LoginController()

        val loginButton = findViewById<LinearLayout>(R.id.loginButtonContainer)
        val registerLink = findViewById<TextView>(R.id.registerTextView)
        val googleLogin = findViewById<LinearLayout>(R.id.googleLoginContainer)

        val errorLabel = findViewById<TextView>(R.id.erroreLabel)

        loginButton.setOnClickListener{
            login(errorLabel)
        }

        registerLink.setOnClickListener {
            goToRegister()
        }

        googleLogin.setOnClickListener {
            loginExternal(errorLabel)
        }
    }

    private fun login(errorLabel: TextView) {
        val email = findViewById<EditText>(R.id.emailHintTextView).text.toString()
        val password = findViewById<EditText>(R.id.passwordHintTextView).text.toString()

        if (loginController.areValid(email, password)){
            loginController.loginWithAmplify(email, password, errorLabel)
        }
        else {
            errorLabel.visibility = TextView.VISIBLE
        }
    }

    private fun loginExternal(errorLabel: TextView) {
        loginController.loginWithThirdProviders(errorLabel, this)
    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}