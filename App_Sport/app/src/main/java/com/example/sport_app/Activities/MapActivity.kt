package com.example.sport_app.Activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sport_app.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // Locations
    private val location1 = LatLng(4.6067, -74.0817)
    private val location2 = LatLng(3.4516, -76.5320)
    private val location3 = LatLng(10.3927, -75.5144)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupButtonListeners()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        moveToLocation(location1, "Location 1")
    }

    private fun setupButtonListeners(){
        findViewById<Button>(R.id.btn_location_1).setOnClickListener{
            moveToLocation(location1, "Location 1")
        }
        findViewById<Button>(R.id.btn_location_2).setOnClickListener{
            moveToLocation(location2, "Location 2")
        }
        findViewById<Button>(R.id.btn_location_3).setOnClickListener{
            moveToLocation(location3, "Location 3")
        }
    }

    private fun moveToLocation(location: LatLng, title: String){

        if (::mMap.isInitialized) {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(location).title(title))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
        }
    }
}