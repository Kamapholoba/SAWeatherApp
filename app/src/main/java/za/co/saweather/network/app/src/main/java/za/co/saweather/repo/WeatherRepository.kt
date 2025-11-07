package za.co.saweather.repo

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.saweather.db.AppDatabase
import za.co.saweather.db.WeatherEntity
import za.co.saweather.network.WeatherApi

class WeatherRepository(private val db: AppDatabase) {
    suspend fun fetchAndCache(lat: Double, lon: Double, cityId: String): String? =
        withContext(Dispatchers.IO) {
            try {
                val resp = WeatherApi.service.getWeather(lat, lon, "YOUR_API_KEY")
                if (resp.isSuccessful) {
                    val json = Gson().toJson(resp.body())
                    db.weatherDao().upsert(
                        WeatherEntity(cityId, json, System.currentTimeMillis())
                    )
                    json
                } else {
                    db.weatherDao().get(cityId)?.jsonData
                }
            } catch (e: Exception) {
                Log.e("WeatherRepo", "Error: ${e.message}")
                db.weatherDao().get(cityId)?.jsonData
            }
        }
}
