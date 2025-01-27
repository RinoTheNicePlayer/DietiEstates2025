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
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.dietiestates25.R
import com.example.dietiestates25.view.HomeClienteActivity
import com.example.dietiestates25.view.MainActivity

class AuthController(private val context: Context){

    fun signUpWithAmplify(email: String, password: String, role: String, errorLabel: TextView, activity: AppCompatActivity){
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
                loginWithAmplify(email, password, errorLabel, activity)
                errorLabel.visibility = TextView.INVISIBLE
                Log.i("Amplify", "Sign up succeeded")
            },
            {
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Amplify", "Sign up failed", it)
            }
        )
    }

    fun loginWithAmplify(email: String, password: String, erroreLabel: TextView, activity: AppCompatActivity){
        Amplify.Auth.signIn(
            email,
            password,
            { result -> if (result.isSignedIn) {
                fetchUserRole(activity)
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

    fun handleRedirection(intent: Intent?, activity: AppCompatActivity) {
        intent?.data?.let { uri ->
            if (uri.scheme == "myapp" && uri.host == "callback") {
                Amplify.Auth.handleWebUISignInResponse(intent)
                fetchUserRole(activity)
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

    fun signOut(activity: AppCompatActivity) {
        Amplify.Auth.signOut { signOutResult ->
            when(signOutResult) {
                is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                    // Sign Out completed fully and without errors.
                    Log.i("AuthQuickStart", "Signed out successfully")
                    navigateTo(activity, MainActivity())
                    activity.finish()
                }
                is AWSCognitoAuthSignOutResult.PartialSignOut -> {
                    // Sign Out completed with some errors. User is signed out of the device.
                    signOutResult.hostedUIError?.let {
                        Log.e("AuthQuickStart", "HostedUI Error", it.exception)
                        // Optional: Re-launch it.url in a Custom tab to clear Cognito web session.

                    }
                    signOutResult.globalSignOutError?.let {
                        Log.e("AuthQuickStart", "GlobalSignOut Error", it.exception)
                        // Optional: Use escape hatch to retry revocation of it.accessToken.
                    }
                    signOutResult.revokeTokenError?.let {
                        Log.e("AuthQuickStart", "RevokeToken Error", it.exception)
                        // Optional: Use escape hatch to retry revocation of it.refreshToken.
                    }
                }
                is AWSCognitoAuthSignOutResult.FailedSignOut -> {
                    // Sign Out failed with an exception, leaving the user signed in.
                    Log.e("AuthQuickStart", "Sign out Failed", signOutResult.exception)
                }
            }
        }
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
                val role = attributes.firstOrNull { it.key.keyString == "custom:role" }?.value
                AuthManager.getInstance().role = role
                goToHome(activity, role)

                Log.i("AuthQuickstart", "User role: $role")
            },
            { error -> Log.e("Auth", "Failed to fetch user attributes", error) }
        )
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
        navigateTo(activity, HomeClienteActivity())
        activity.finish()
    }

    private fun goToHomeAgenti(activity: AppCompatActivity) {
        //navigateTo(activity, SearchActivity()) // TODO: fare home agenti
        activity.finish()
    }
}