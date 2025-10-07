package com.saweatherplus.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey val dt: Long,
    val city: String,
    val temp: Double,
    val condition: String
)
