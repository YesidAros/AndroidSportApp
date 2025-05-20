package com.example.sport_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports")
data class Sport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String
)
