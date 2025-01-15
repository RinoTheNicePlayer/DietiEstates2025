package com.example.dietiestates25.controller

import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class AuthController{

    fun signUpWithAmplify(email: String, password: String, role: String, errorLabel: TextView){
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.custom("role"), role)
        )

        val options = AuthSignUpOptions.builder()
            .userAttributes(attributes)
            .build()

        Amplify.Auth.signUp(
            email,
            password,
            options,
            {
                Log.i("Amplify", "Sign up succeeded")
                loginWithAmplify(email, password, errorLabel)
                errorLabel.visibility = TextView.GONE
            },
            {
                Log.e("Amplify", "Sign up failed", it)
                errorLabel.visibility = TextView.VISIBLE
            }
        )
    }

    fun loginWithAmplify(email: String, password: String, erroreLabel: TextView){
        Amplify.Auth.signIn(
            email,
            password,
            { result -> if (result.isSignInComplete) {
                Log.i("AuthQuickstart", result.toString())
                getToken()
                erroreLabel.visibility = TextView.GONE
            }},
            { error ->
                erroreLabel.visibility = TextView.VISIBLE
                Log.e("AuthQuickstart", error.toString())
            }
        )
    }

    fun loginWithThirdProviders(erroreLabel: TextView, activity: AppCompatActivity){
        Amplify.Auth.signInWithWebUI(
            activity,
            { result -> if (result.isSignInComplete) {
                Log.i("AuthQuickstart", result.toString())
                erroreLabel.visibility = TextView.GONE
            }},
            { error ->
                erroreLabel.visibility = TextView.VISIBLE
                Log.e("AuthQuickstart", error.toString())
            }
        )
    }

    fun handleRedirection(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.scheme == "myapp" && uri.host == "callback") {
                getToken()
            }
        }
    }

    fun signUpGestoriOrAgenti(email: String, password: String, role: String, errorLabel: TextView){
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.custom("role"), role)
        )

        val options = AuthSignUpOptions.builder()
            .userAttributes(attributes)
            .build()

        Amplify.Auth.signUp(
            email,
            password,
            options,
            {
                Log.i("Amplify", "Sign up succeeded")
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

    private fun getToken() {
        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                val idToken = (session as AWSCognitoAuthSession).userPoolTokens.value?.idToken
                TokenManager.getInstance().idToken = idToken
                ///TODO: invio token al server
                ///TODO: redirect to home
                ///TODO: fetch user role
            }},
            { error -> Log.e("Auth", "Failed to fetch auth session", error) }
        )
    }

    /*
    private fun fetchUserRole() {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val role = attributes.firstOrNull { it.key.keyString == "custom:role" }?.value
                Log.i("AuthQuickstart", "User role: $role")
                // Handle the role as needed
            },
            { error -> Log.e("Auth", "Failed to fetch user attributes", error) }
        )
    }
     */
}