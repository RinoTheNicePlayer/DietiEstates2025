package com.example.dietiestates25.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dietiestates25.R
import com.example.dietiestates25.view.fragment.HomeAgentFragment
import com.example.dietiestates25.view.fragment.OfferAgentFragment
import com.example.dietiestates25.view.fragment.ProfileFragment
import com.example.dietiestates25.view.fragment.ReservationAgentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeAgentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbar_agent)

        // Set the initial fragment
        replaceFragment(HomeAgentFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation2View)

        bottomNavigationView.setOnItemSelectedListener { item ->
            navigationTab(item)
        }

        // Add OnBackStackChangedListener to update the visibility of the up button
        supportFragmentManager.addOnBackStackChangedListener {
            updateUpButtonVisibility()
        }
        // Initial call to set the correct visibility
        updateUpButtonVisibility()
    }

    private fun navigationTab(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home2 -> replaceFragment(HomeAgentFragment())
            R.id.nav_offerte -> replaceFragment(OfferAgentFragment())
            R.id.nav_reservation -> replaceFragment(ReservationAgentFragment())
            R.id.nav_profile2 -> replaceFragment(ProfileFragment())
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container2, fragment)
            .commit()
    }

    private fun updateUpButtonVisibility() {
        supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
    }

}