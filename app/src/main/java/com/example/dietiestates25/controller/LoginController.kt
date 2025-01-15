package com.example.dietiestates25.controller

import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify

class LoginController{

    fun loginWithAmplify(email: String, password: String, erroreLabel: TextView){
        Amplify.Auth.signIn(
            email,
            password,
            { result -> if (result.isSignInComplete) {
                getToken(result)
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
                getToken(result)
                erroreLabel.visibility = TextView.GONE
            }},
            { error ->
                erroreLabel.visibility = TextView.VISIBLE
                Log.e("AuthQuickstart", error.toString()) }
        )
    }

    fun areValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun getToken(result: AuthSignInResult) {
        Log.i("AuthQuickstart", result.toString())

        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                val idToken = (session as AWSCognitoAuthSession).userPoolTokens.value?.idToken
                TokenManager.getInstance().idToken = idToken
            }},
            { error -> Log.e("Auth", "Failed to fetch auth session", error) }
        )
    }
}