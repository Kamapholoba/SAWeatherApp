package za.co.saweather.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val cityId: String,
    val jsonData: String,
    val lastUpdated: Long
)
