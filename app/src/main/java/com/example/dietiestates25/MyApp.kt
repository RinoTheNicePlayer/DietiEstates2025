package com.example.dietiestates25

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        setupAmplify()
    }

    private fun setupAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
            Log.i("Amplify", "Initialized Amplify")
        }catch (error: AmplifyException){
            Log.e("Amplify", "Could not initialize Amplify", error)
        }
    }
}