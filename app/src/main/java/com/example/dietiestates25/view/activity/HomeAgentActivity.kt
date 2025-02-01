package com.example.dietiestates25.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.PropertyAdapter
import com.example.dietiestates25.model.Property
import com.example.dietiestates25.view.fragment.HomeAgentFragment
import com.example.dietiestates25.view.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeAgentActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyList = mutableListOf<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_agent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Imposta il fragment iniziale sulla home
        replaceFragment(HomeAgentFragment())

        // Configura la BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation2View)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home2 -> replaceFragment(HomeAgentFragment())
                R.id.nav_offerte -> {
                    // Navigazione futura per Offerte
                }
                R.id.nav_reservation -> {
                    // Navigazione futura per Prenotazioni
                }
                R.id.nav_profile2 -> replaceFragment(ProfileFragment())
            }
            true
        }

        // Configura il RecyclerView per gli immobili dell'agente
        recyclerView = findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadAgentProperties()

        propertyAdapter = PropertyAdapter(propertyList)
        recyclerView.adapter = propertyAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container2, fragment)
            .commit()
    }

    private fun loadAgentProperties() {
        propertyList.apply {
            add(Property("Appartamento 1", "€250,000", ""))
            add(Property("Villa 2", "€500,000", ""))
            add(Property("Loft 3", "€320,000", ""))
            add(Property("Monolocale 4", "€150,000", ""))
            add(Property("Attico 5", "€600,000", ""))
        }
    }
}