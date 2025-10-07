package com.saweatherplus.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast ORDER BY dt DESC")
    suspend fun getAll(): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ForecastEntity>)

    @Query("DELETE FROM forecast")
    suspend fun clear()
}
