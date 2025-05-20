package com.example.sport_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sport_app.data.local.Sport
import com.example.sport_app.data.local.SportDao

@Database(entities = [User::class, Sport::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun sportDao(): SportDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sport_app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
