package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthController

class RegistrationManagerFragment : Fragment() {
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authController = AuthController(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration_manager, container, false)
        val email = view.findViewById<EditText>(R.id.email_gestore_hint).text.toString()
        val password = view.findViewById<EditText>(R.id.password_gestore_hint).text.toString()
        val registerManagerButton = view.findViewById<LinearLayout>(R.id.register_manager_button)
        val errorLabel = view.findViewById<TextView>(R.id.errorGestoreLabel)

        registerManagerButton.setOnClickListener {
            if (areValid(email, password)) {
                registerManager(email, password, errorLabel)
                errorLabel.visibility = View.INVISIBLE
            }
            else {
                errorLabel.visibility = View.VISIBLE
            }
        }

        return view
    }

    private fun registerManager(email: String, password: String, errorLabel: TextView) {
        authController.signUpGestoreOrAgente(email,password, "Gestore", errorLabel) {
            goBack()
        }
    }

    private fun areValid(oldPassword: String, newPassword: String): Boolean {
        return oldPassword.isNotEmpty() && newPassword.isNotEmpty()
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }
}