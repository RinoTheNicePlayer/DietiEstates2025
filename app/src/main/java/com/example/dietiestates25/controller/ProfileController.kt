package com.example.dietiestates25.controller

import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.core.Amplify
import com.example.dietiestates25.model.RegistrationRequest
import com.example.dietiestates25.view.activity.MainActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class ProfileController {
    fun signUpGestoreOrAgente(email: String, password: String, role: String, errorLabel: TextView, onSuccess: () -> Unit){
        val client = OkHttpClient()
        val url = "/auth/register" /// TODO: da cambiare
        val token = AuthManager.instance?.idToken

        val json = Json.encodeToString(RegistrationRequest(email, password, role))
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                errorLabel.visibility = TextView.VISIBLE
                Log.e("Backend", "Failed to send data", e)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody: ResponseBody = response.body
                    val responseMessage = responseBody.string()
                    errorLabel.visibility = TextView.INVISIBLE
                    Log.i("Backend", "Data sent successfully: $responseMessage")
                    onSuccess()
                } else {
                    errorLabel.visibility = TextView.VISIBLE
                    Log.e("Backend", "Failed to send data: ${response.message}")
                }
            }
        })
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

    private fun comeBackToLogin(fragment: Fragment){
        val intent = Intent(fragment.requireContext(), MainActivity::class.java)
        fragment.startActivity(intent)
        fragment.requireActivity().finish()
    }
}