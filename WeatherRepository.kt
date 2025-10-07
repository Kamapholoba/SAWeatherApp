package com.saweatherplus.data.repository

import com.saweatherplus.data.api.WeatherApi
import com.saweatherplus.data.db.ForecastDao
import com.saweatherplus.data.db.ForecastEntity

class WeatherRepository(private val api: WeatherApi, private val dao: ForecastDao, private val apiKey: String) {
    suspend fun refreshAndCache(lat: Double, lon: Double): Result<List<ForecastEntity>> {
        return try {
            val response = api.oneCall(lat, lon, apiKey = apiKey)
            if (response.isSuccessful) {
                val body = response.body()!!
                val items = body.daily.map { day ->
                    ForecastEntity(
                        dt = day.dt,
                        city = body.timezone ?: "Unknown",
                        temp = day.temp.day,
                        condition = day.weather.firstOrNull()?.main ?: "Unknown"
                    )
                }
                dao.insertAll(items)
                Result.success(items)
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            val cached = dao.getAll()
            if (cached.isNotEmpty()) Result.success(cached) else Result.failure(e)
        }
    }
}
