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

class UpdatePasswordFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_update_password, container, false)
        val oldPassword = view.findViewById<EditText>(R.id.old_password_hint).text.toString()
        val newPassword = view.findViewById<EditText>(R.id.new_password_hint).text.toString()
        val changePasswordButton = view.findViewById<LinearLayout>(R.id.change_password_button)
        val errorLabel = view.findViewById<TextView>(R.id.errorPasswordLabel)

        changePasswordButton.setOnClickListener {
            if (areValid(oldPassword, newPassword)) {
                updatePassword(oldPassword, newPassword, errorLabel)
                errorLabel.visibility = View.INVISIBLE
            }
            else {
                errorLabel.visibility = View.VISIBLE
            }
        }

        return view
    }

    private fun areValid(oldPassword: String, newPassword: String): Boolean {
        return oldPassword.isNotEmpty() && newPassword.isNotEmpty()
    }

    private fun updatePassword(oldPassword: String, newPassword: String, errorLabel: TextView) {
        authController.updatePassword(oldPassword, newPassword, errorLabel) {
            goBack()
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }
}