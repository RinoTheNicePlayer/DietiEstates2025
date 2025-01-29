package com.example.dietiestates25.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import com.example.dietiestates25.R

class ProfileAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_admin)

        findViewById<LinearLayout>(R.id.navbar_item_home_state).setBackgroundResource(android.R.color.transparent)
        findViewById<LinearLayout>(R.id.navbar_item_offers_state).setBackgroundResource(android.R.color.transparent)
        findViewById<LinearLayout>(R.id.navbar_item_reservations_state).setBackgroundResource(
            android.R.color.transparent
        )
        findViewById<LinearLayout>(R.id.navbar_item_profile_state).setBackgroundResource(R.drawable.button_state)
    }
}