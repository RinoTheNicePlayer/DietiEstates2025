package com.example.dietiestates25

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        setupAmplify()
    }

    private fun setupAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i("Amplify", "Initialized Amplify")
        }catch (error: AmplifyException){
            Log.e("Amplify", "Could not initialize Amplify", error)
        }
    }
}