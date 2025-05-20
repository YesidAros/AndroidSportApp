package com.example.sport_app.data.local

import androidx.room.*

@Dao
interface SportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSport(sport: Sport)

    @Query("SELECT * FROM sports")
    suspend fun getAllSports(): List<Sport>

    @Delete
    suspend fun deleteSport(sport: Sport)
}
