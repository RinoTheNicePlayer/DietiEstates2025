package com.example.dietiestates25.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dietiestates25.R
import com.example.dietiestates25.view.fragment.HomeAgentFragment
import com.example.dietiestates25.view.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeAgentActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_agent) // per il momento ho fatto su home.xml

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(HomeAgentFragment())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation2View)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home2 -> {
                    replaceFragment(HomeAgentFragment())
                }
                R.id.nav_offerte -> {
                    // go to
                }
                R.id.nav_reservation -> {
                    // go to
                }
                R.id.nav_profile2 -> {
                    replaceFragment(ProfileFragment())
                }
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