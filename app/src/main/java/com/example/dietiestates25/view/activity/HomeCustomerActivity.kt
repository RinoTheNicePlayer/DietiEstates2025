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
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeCustomerFragment())
                }
                R.id.nav_riepilogo -> {
                    replaceFragment(SummaryCustomerFragment())
                }
                R.id.nav_profile -> {
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
            .replace(R.id.fragment_layout_container, fragment)
            .commit()
    }
}