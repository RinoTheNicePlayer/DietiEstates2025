package com.example.dietiestates25.controller

import android.util.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.core.Amplify

class AuthManager private constructor() {
    @get:Synchronized
    val idToken: String?
        get() {
            return getToken()
        }

    var role: String? = null

    private fun getToken(): String? {
        var idToken: String? = null

        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                idToken = (session as AWSCognitoAuthSession).userPoolTokensResult.value?.idToken
            }},
            { error -> Log.e("Auth", "Failed to fetch auth session", error) }
        )

        return idToken
    }


    companion object {
        @get:Synchronized
        var instance: AuthManager? = null
            get() {
                if (field == null) {
                    field = AuthManager()
                }
                return field
            }
            private set
    }
}
