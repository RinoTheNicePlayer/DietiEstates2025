package com.example.dietiestates25.controller

import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.dietiestates25.view.activity.HomeAgentActivity
import com.example.dietiestates25.view.activity.HomeCustomerActivity
import com.example.dietiestates25.view.activity.MainActivity

class AuthController {

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

    fun signUpGestoreOrAgente(email: String, password: String, role: String, errorLabel: TextView, onSuccess: () -> Unit){
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
                onSuccess()
            },
            {
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Amplify", "Sign up failed", it)
            }
        )
    }

    fun updatePassword(oldPassword: String, newPassword: String, errorLabel: TextView, onSuccess: () -> Unit) {
        Amplify.Auth.updatePassword(
            oldPassword,
            newPassword,
            {
                errorLabel.visibility = TextView.INVISIBLE
                Log.i("Amplify", "Password updated successfully")
                onSuccess()
            },
            { error ->
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Amplify", "Password update failed", error)
            }
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

    fun signOut(fragment: Fragment) {
        Amplify.Auth.signOut { signOutResult ->
            when(signOutResult) {
                is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                    // Sign Out completed fully and without errors.
                    Log.i("AuthQuickStart", "Signed out successfully")
                    comeBackToLogin(fragment)
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

    fun fetchUserMail(callback: (String?) -> Unit) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val email = attributes.firstOrNull { it.key == AuthUserAttributeKey.email() }?.value
                Log.i("AuthQuickstart", "User email: $email")
                callback(email)
            },
            { error ->
                Log.e("Auth", "Failed to fetch user attributes", error)
                callback(null)
            }
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
                    val role = attributes.firstOrNull { it.key.keyString == "custom:role" }?.value
                    AuthManager.getInstance().role = role
                    Log.i("AuthQuickstart", "User role: $role")
                    goToHome(activity, role)
                }
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
        navigateTo(activity, HomeCustomerActivity())
        activity.finish()
    }

    private fun goToHomeAgenti(activity: AppCompatActivity) {
        navigateTo(activity, HomeAgentActivity())
        activity.finish()
    }

    private fun comeBackToLogin(fragment: Fragment){
        val intent = Intent(fragment.requireContext(), MainActivity::class.java)
        fragment.startActivity(intent)
        fragment.requireActivity().finish()
    }
}