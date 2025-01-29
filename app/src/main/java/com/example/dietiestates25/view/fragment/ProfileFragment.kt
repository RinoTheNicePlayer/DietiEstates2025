package com.example.dietiestates25.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthController
import com.example.dietiestates25.controller.AuthManager

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val role = AuthManager.getInstance().role
        val roleLabel = view.findViewById<TextView>(R.id.role_label)
        val updatePasswordButton = view.findViewById<LinearLayout>(R.id.change_password_button)
        val registerGestoreButton = view.findViewById<LinearLayout>(R.id.register_manager_button)
        val registerAgenteButton = view.findViewById<LinearLayout>(R.id.register_agent_button)
        val signOutButton = view.findViewById<LinearLayout>(R.id.logout_button)

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

        return view
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
        val intent = Intent(requireContext(), nextActivity::class.java)
        startActivity(intent)
    }
}