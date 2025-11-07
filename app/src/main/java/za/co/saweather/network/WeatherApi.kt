package za.co.saweather.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

data class WeatherResponse(
    val current: CurrentWeather?,
    val hourly: List<Hourly>?,
    val daily: List<Daily>?
)

data class CurrentWeather(val dt: Long, val temp: Double, val weather: List<WeatherDesc>)
data class Hourly(val dt: Long, val temp: Double, val weather: List<WeatherDesc>)
data class Daily(val dt: Long, val temp: Temp, val weather: List<WeatherDesc>)
data class Temp(val day: Double, val min: Double, val max: Double)
data class WeatherDesc(val id: Int, val main: String, val description: String, val icon: String)

interface WeatherApiService {
    // Using OneCall endpoint (replace with current OpenWeather variants)
    @GET("data/2.5/onecall")
    suspend fun getOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}

object WeatherApi {
    private const val BASE_URL = "https://api.openweathermap.org/"

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val retrofitService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}
