package com.example.dietiestates25.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dietiestates25.R
import com.example.dietiestates25.view.fragment.HomeCustomerFragment
import com.example.dietiestates25.view.fragment.ProfileFragment
import com.example.dietiestates25.view.fragment.SummaryCustomerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeCustomerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbar_customer)

        replaceFragment(HomeCustomerFragment())
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavBar.setOnItemSelectedListener { item ->
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
            R.id.nav_home -> replaceFragment(HomeCustomerFragment())
            R.id.nav_riepilogo -> replaceFragment(SummaryCustomerFragment())
            R.id.nav_profile -> replaceFragment(ProfileFragment())
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
            .replace(R.id.fragment_layout_container, fragment)
            .commit()
    }

    private fun updateUpButtonVisibility() {
        supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
    }
}