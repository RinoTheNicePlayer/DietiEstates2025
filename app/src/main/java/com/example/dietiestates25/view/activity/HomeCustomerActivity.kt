package com.example.dietiestates25.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dietiestates25.R
import com.example.dietiestates25.view.fragment.HomeCustomerFragment
import com.example.dietiestates25.view.fragment.ProfileCustomerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeCustomerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_customer)

        replaceFragment(HomeCustomerFragment())
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeCustomerFragment())
                }
                R.id.nav_riepilogo -> {
                    // go to
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileCustomerFragment())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout_container, fragment)
            .commit()
    }
}