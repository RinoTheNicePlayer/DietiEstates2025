package com.example.dietiestates25.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthController
import com.example.dietiestates25.controller.AuthManager

class ProfileActivity: AppCompatActivity() {
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        authController = AuthController(this)

        val role = AuthManager.getInstance().role

        val roleLabel = findViewById<TextView>(R.id.roleLabel)
        val updatePasswordButton = findViewById<LinearLayout>(R.id.updatePasswordContainer)
        val registerGestoreButton = findViewById<LinearLayout>(R.id.registerGestoreContainer)
        val registerAgenteButton = findViewById<LinearLayout>(R.id.registerAgenteContainer)
        val signOutButton = findViewById<LinearLayout>(R.id.logOutContainer)

        roleLabel.text = role

        orderVisibilityButtonForRole(role, updatePasswordButton, registerGestoreButton, registerAgenteButton)

        signOutButton.setOnClickListener {
            signOut()
        }

        updatePasswordButton.setOnClickListener {
            // deve andare in schermata aggiorna password
        }

        registerGestoreButton.setOnClickListener {
            // deve andare in schermata registrazione gestore
        }

        registerAgenteButton.setOnClickListener {
            // deve andare in schermata registrazione agente
        }
    }

    private fun orderVisibilityButtonForRole(
        role: String, updatePasswordButton: LinearLayout, registerGestoreButton: LinearLayout, registerAgenteButton: LinearLayout
    ) {
        if (role == "Cliente" || role == "AgenteImmobiliare") {
            updatePasswordButton.visibility = LinearLayout.GONE
            registerGestoreButton.visibility = LinearLayout.GONE
            registerAgenteButton.visibility = LinearLayout.GONE
        }
        else if (role == "Gestore") {
            updatePasswordButton.visibility = LinearLayout.GONE
            registerGestoreButton.visibility = LinearLayout.GONE
            registerAgenteButton.visibility = LinearLayout.VISIBLE
        }
        else {
            updatePasswordButton.visibility = LinearLayout.VISIBLE
            registerGestoreButton.visibility = LinearLayout.VISIBLE
            registerAgenteButton.visibility = LinearLayout.VISIBLE
        }
    }

    private fun signOut() {
        authController.signOut(this)
    }

    private fun navigateTo(nextActivity: AppCompatActivity) {
        val intent = Intent(this, nextActivity::class.java)
        startActivity(intent)
    }
}