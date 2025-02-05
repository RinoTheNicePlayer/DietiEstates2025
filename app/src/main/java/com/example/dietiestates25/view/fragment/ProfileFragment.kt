package com.example.dietiestates25.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.AuthManager
import com.example.dietiestates25.controller.ProfileController

class ProfileFragment : Fragment() {
    private lateinit var profileController: ProfileController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileController = ProfileController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val role = AuthManager.getInstance().role
        val emailUser = view.findViewById<TextView>(R.id.email_value)
        val roleLabel = view.findViewById<TextView>(R.id.role_label)
        val updatePasswordButton = view.findViewById<LinearLayout>(R.id.change_password_button)
        val registerGestoreButton = view.findViewById<LinearLayout>(R.id.register_manager_button)
        val registerAgenteButton = view.findViewById<LinearLayout>(R.id.register_agent_button)
        val signOutButton = view.findViewById<LinearLayout>(R.id.logout_button)

        roleLabel.text = role
        fetchUserMail(emailUser)
        orderVisibilityButtonForRole(role, updatePasswordButton, registerGestoreButton, registerAgenteButton)

        signOutButton.setOnClickListener {
            signOut()
        }

        updatePasswordButton.setOnClickListener {
            navigateTo(UpdatePasswordFragment())
        }

        registerGestoreButton.setOnClickListener {
            navigateTo(RegistrationManagerFragment())
        }

        registerAgenteButton.setOnClickListener {
            navigateTo(RegistrationAgentFragment())
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
        profileController.signOut(this)
    }

    private fun fetchUserMail(emailUser: TextView) {
        profileController.fetchUserMail { email ->
            activity?.runOnUiThread{
                if (email != null) {
                    emailUser.text = email
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container2, fragment)
            .addToBackStack(null)
            .commit()
    }
}