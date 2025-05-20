package com.example.sport_app.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sport_app.R
import android.content.Intent
import android.widget.Button

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_results)

        val btnLocations = findViewById<Button>(R.id.btn_locations)
        btnLocations.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

    }


}