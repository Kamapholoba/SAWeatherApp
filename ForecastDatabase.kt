package com.saweatherplus.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ForecastEntity::class], version = 1)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao

    companion object {
        @Volatile private var INSTANCE: ForecastDatabase? = null

        fun getDatabase(context: Context): ForecastDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForecastDatabase::class.java,
                    "forecast_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
