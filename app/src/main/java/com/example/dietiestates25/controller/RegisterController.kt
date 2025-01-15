package com.example.dietiestates25.controller

import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class RegisterController {

    fun signUpWithAmplify(email: String, password: String, errorLabel: TextView, activity: AppCompatActivity){
        Amplify.Auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().build(),
            {
                Log.i("Amplify", "Sign up succeeded")
                activity.finish() //chiude current activity
                errorLabel.visibility = TextView.GONE
            },
            {
                Log.e("Amplify", "Sign up failed", it)
                errorLabel.visibility = TextView.VISIBLE
            }
        )
    }

    fun areValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}