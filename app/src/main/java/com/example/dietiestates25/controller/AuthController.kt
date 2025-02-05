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
import com.example.dietiestates25.view.activity.HomeAgentActivity
import com.example.dietiestates25.view.activity.HomeCustomerActivity

class AuthController {

    fun signUpWithAmplify(email: String, password: String, role: String, errorLabel: TextView, activity: AppCompatActivity){
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.email(), email),
            AuthUserAttribute(AuthUserAttributeKey.custom("custom:role"), role)
        )

        val options = AuthSignUpOptions.builder()
            .userAttributes(attributes)
            .build()

        Amplify.Auth.signUp(
            email,
            password,
            options,
            {
                activity.runOnUiThread {
                    loginWithAmplify(email, password, errorLabel, activity)
                    errorLabel.visibility = TextView.INVISIBLE
                }
                Log.i("Amplify", "Sign up succeeded")
            },
            {
                activity.runOnUiThread {
                    errorLabel.visibility = TextView.VISIBLE
                }
                Log.e("Amplify", "Sign up failed", it)
            }
        )
    }

    fun loginWithAmplify(email: String, password: String, erroreLabel: TextView, activity: AppCompatActivity){
        Amplify.Auth.signIn(
            email,
            password,
            { result -> if (result.isSignedIn) {
                activity.runOnUiThread {
                    fetchUserRole(activity)
                    erroreLabel.visibility = TextView.INVISIBLE
                }
                Log.i("AuthQuickstart", result.toString())
            }},
            { error ->
                activity.runOnUiThread{
                    erroreLabel.visibility = TextView.VISIBLE
                }
                Log.e("AuthQuickstart", error.toString())
            }
        )
    }

    fun loginWithThirdProviders(activity: AppCompatActivity){
        Amplify.Auth.signInWithWebUI(
            activity,
            {
                Log.i("AuthQuickStart", "Signin OK = $it")
                activity.runOnUiThread {
                    fetchUserRole(activity)
                }
            },
            { Log.e("AuthQuickStart", "Signin failed", it) }
        )
    }

    fun userIsSignedIn(activity: AppCompatActivity) {
        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                activity.runOnUiThread {
                    fetchUserRole(activity)
                }
            }},
            { error -> Log.e("Auth", "Failed to fetch auth session", error) }
        )
    }

    private fun getToken() {
        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                val idToken = (session as AWSCognitoAuthSession).userPoolTokensResult.value?.idToken
                AuthManager.getInstance().idToken = idToken
            }},
            { error -> Log.e("Auth", "Failed to fetch auth session", error) }
        )
    }

    private fun fetchUserRole(activity: AppCompatActivity) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                activity.runOnUiThread {
                    var role = attributes.firstOrNull { it.key.keyString == "custom:role" }?.value
                    role = setRoleToClienteIfNull(role)
                    AuthManager.getInstance().role = role
                    Log.i("AuthQuickstart", "User role: $role")
                    goToHome(activity, role)
                }
            },
            { error -> Log.e("Auth", "Failed to fetch user attributes", error) }
        )
    }

    private fun setRoleToClienteIfNull(role: String?): String {
        if (role == null) {
            val roleAttribute = "Cliente"
            val attribute = AuthUserAttribute(AuthUserAttributeKey.custom("custom:role"), roleAttribute)

            Amplify.Auth.updateUserAttribute(
                attribute,
                { Log.i("AuthQuickstart", "User role updated") },
                { Log.e("AuthQuickstart", "User role update failed", it) }
            )

            return roleAttribute
        } else {
            return role
        }
    }

    fun navigateTo(actualActivity: AppCompatActivity, nextActivity: AppCompatActivity){
        val intent = Intent(actualActivity, nextActivity::class.java)
        actualActivity.startActivity(intent)
    }

    fun areValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun goToHome(activity: AppCompatActivity, role: String?){
        if (role == "Cliente" || role == null){
            goToHomeClienti(activity)
        } else {
            goToHomeAgenti(activity)
        }
    }

    private fun goToHomeClienti(activity: AppCompatActivity){
        navigateTo(activity, HomeCustomerActivity())
        activity.finish()
    }

    private fun goToHomeAgenti(activity: AppCompatActivity) {
        navigateTo(activity, HomeAgentActivity())
        activity.finish()
    }
}