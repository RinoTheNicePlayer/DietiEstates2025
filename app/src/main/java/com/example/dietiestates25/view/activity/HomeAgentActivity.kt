package com.example.dietiestates25.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dietiestates25.R
import com.example.dietiestates25.view.fragment.HomeAgentFragment
import com.example.dietiestates25.view.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeAgentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbar_agent)

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

}