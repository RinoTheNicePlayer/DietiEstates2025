package com.example.dietiestates25.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PropertyDetailsActivity: AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_details)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val property = intent.getParcelableExtra<Property>("property")
        val latLng = LatLng(property.latitude, property.longitude)
        googleMap.addMarker(MarkerOptions().position(latLng).title(property.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }
}