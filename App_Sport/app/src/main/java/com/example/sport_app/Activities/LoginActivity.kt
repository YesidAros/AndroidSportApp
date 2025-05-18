package com.example.sport_app.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sport_app.R

class LoginActivity : AppCompatActivity() {

    private lateinit var btn_google: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_google = findViewById(R.id.bt_google)

        btn_google.setOnClickListener{
            intent = Intent(this,LoginActivity::class.java)
        }

    }

}