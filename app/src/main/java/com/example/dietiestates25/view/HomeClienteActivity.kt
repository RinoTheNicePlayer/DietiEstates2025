package com.example.dietiestates25.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.controller.HomeClienteController
import com.example.dietiestates25.R

class HomeClienteActivity: AppCompatActivity() {
    private lateinit var homeClienteController: HomeClienteController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeClienteController = HomeClienteController(this)

        val searchButton = findViewById<LinearLayout>(R.id.searchButton)

        searchButton.setOnClickListener {
            goToSearch()
        }
    }

    private fun goToSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}