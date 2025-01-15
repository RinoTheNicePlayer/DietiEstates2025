package com.example.dietiestates25.controller

import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.core.Amplify

class LoginController{

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

    fun areValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun getToken() {
        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                val idToken = (session as AWSCognitoAuthSession).userPoolTokens.value?.idToken
                TokenManager.getInstance().idToken = idToken
            }},
            { error -> Log.e("Auth", "Failed to fetch auth session", error) }
        )
    }
}