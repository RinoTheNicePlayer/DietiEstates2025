package com.example.dietiestates25.controller

import android.util.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.core.Amplify

object AuthManager {
    var role: String? = null

    fun getToken(callBack: (String?) -> Unit) {
        Amplify.Auth.fetchAuthSession(
            { session -> if (session.isSignedIn) {
                val idToken = (session as AWSCognitoAuthSession).userPoolTokensResult.value?.idToken
                Log.i("AuthQuickstart", "id Token: $idToken")
                callBack(idToken)
            }},
            {
                error -> Log.e("Auth", "Failed to fetch auth session", error)
                callBack(null)
            }
        )
    }

}
