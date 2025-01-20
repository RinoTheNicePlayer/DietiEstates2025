package com.example.dietiestates25.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.dietiestates25.R
import com.example.dietiestates25.view.HomeClienteActivity
import com.example.dietiestates25.view.SearchActivity

class AuthController(private val context: Context){

    fun signUpWithAmplify(email: String, password: String, role: String, errorLabel: TextView){
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.email(), email),
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
                loginWithAmplify(email, password, errorLabel)
                errorLabel.visibility = TextView.INVISIBLE
                Log.i("Amplify", "Sign up succeeded")
            },
            {
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Amplify", "Sign up failed", it)
            }
        )
    }

    fun loginWithAmplify(email: String, password: String, erroreLabel: TextView){
        Amplify.Auth.signIn(
            email,
            password,
            { result -> if (result.isSignedIn) {
                fetchUserRole()
                erroreLabel.visibility = TextView.INVISIBLE
                Log.i("AuthQuickstart", result.toString())
            }},
            { error ->
                erroreLabel.visibility = TextView.VISIBLE
                Log.e("AuthQuickstart", error.toString())
            }
        )
    }

    fun loginWithThirdProviders(activity: AppCompatActivity){
        val url = context.getString(R.string.domain_link)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        activity.startActivity(intent)
    }

    fun handleRedirection(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.scheme == "myapp" && uri.host == "callback") {
                Amplify.Auth.handleWebUISignInResponse(intent)
                fetchUserRole()
            }
        }
    }

    fun signUpGestoreOrAgente(email: String, password: String, role: String, errorLabel: TextView){
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.email(), email),
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
                errorLabel.visibility = TextView.INVISIBLE
                Log.i("Amplify", "Sign up succeeded")
            },
            {
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Amplify", "Sign up failed", it)
            }
        )
    }

    fun updatePassword(oldPassword: String, newPassword: String, errorLabel: TextView) {
        Amplify.Auth.updatePassword(
            oldPassword,
            newPassword,
            {
                errorLabel.visibility = TextView.INVISIBLE
                Log.i("Amplify", "Password updated successfully")
            },
            { error ->
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Amplify", "Password update failed", error)
            }
        )
    }

    fun areValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
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

    private fun fetchUserRole() {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val role = attributes.firstOrNull { it.key.keyString == "custom:role" }?.value
                AuthManager.getInstance().role = role
                goToHome(role)

                Log.i("AuthQuickstart", "User role: $role")
            },
            { error -> Log.e("Auth", "Failed to fetch user attributes", error) }
        )
    }

    private fun goToHome(role: String?){
        if (role == "Cliente" || role == null){
            goToHomeClienti()
        } else {
            goToHomeAgenti()
        }
    }

    private fun goToHomeClienti(){
        val intent = Intent(context, HomeClienteActivity::class.java)
        context.startActivity(intent)
    }

    private fun goToHomeAgenti() {
        val intent = Intent(context, SearchActivity::class.java) //TODO: non creata ancora
        context.startActivity(intent)
    }
}