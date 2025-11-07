package za.co.saweather.db

import androidx.room.*

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: WeatherEntity)

    @Query("SELECT * FROM weather_cache WHERE cityId = :id")
    suspend fun get(id: String): WeatherEntity?
}
