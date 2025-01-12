package com.example.dietiestates25.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R

class SearchActivity: AppCompatActivity() {
    /// TODO: Activity della view di cerca immobili

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_property)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // Mostra il tasto indietro nella ActionBar

        /// qui funzioni
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}