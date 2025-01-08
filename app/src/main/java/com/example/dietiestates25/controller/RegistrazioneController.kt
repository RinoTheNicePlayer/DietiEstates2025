package com.example.dietiestates25.controller

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.model.Utente
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class RegistrazioneController(private val context: Context) {
    private val client = OkHttpClient()

    fun registerUser(email: String, password: String, group: String = "Clienti"){
        val utente = Utente(email, password, group)
        val json = Json.encodeToString(utente)

        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url("https://your-backend-url.com/api/register") // Replace this with your backend URL
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                showToast("Registrazione fallita")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        showToast("Registrazione fallita")
                        throw IOException("Registrazione fallita $it")
                    }
                    showToast("Registrazione completata")
                }
            }
        })
    }

    fun areValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun showToast(message: String) {
        (context as? AppCompatActivity)?.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}