package com.example.sport_app

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.sport_app.data.AppDatabase
import com.example.sport_app.data.User
import com.example.sport_app.data.local.Sport
// Make sure to import your Fragment_Search
import com.example.sport_app.Activities.ResultsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Starting Database
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            // add user
            val user = User(email = "elkin@example.com", password = "1234")
            db.userDao().insertUser(user)

            // Consulting user by email
            val fetchedUser = db.userDao().getUserByEmail("elkin@example.com")
            Log.d("ROOM_TEST", "User: $fetchedUser")

            // add Sport
            val sport = Sport(name = "Soccer", type = "Competition")
            db.sportDao().insertSport(sport)

            // Consulting Sport
            val sportsList = db.sportDao().getAllSports()
            Log.d("ROOM_TEST", "Sports: $sportsList")
        }

    }
}