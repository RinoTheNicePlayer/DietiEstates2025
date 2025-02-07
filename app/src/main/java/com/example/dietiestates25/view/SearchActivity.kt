package com.example.dietiestates25.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietiestates25.R
import com.example.dietiestates25.adapter.PropertyAdapter
import com.example.dietiestates25.model.PropertyTest

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private val propertyTestList = mutableListOf<PropertyTest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.recycler_view_properties)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadProperties()

        propertyAdapter = PropertyAdapter(propertyTestList)
        recyclerView.adapter = propertyAdapter
    }

    private fun loadProperties() {
        propertyTestList.apply {
            add(PropertyTest("Immobile#1", "€100,000", ""))
            add(PropertyTest("Immobile#2", "€150,000", ""))
            add(PropertyTest("Immobile#3", "€200,000", ""))
        }
    }
}