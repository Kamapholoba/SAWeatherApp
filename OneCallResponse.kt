package com.saweatherplus.data.model

data class OneCallResponse(
    val timezone: String?,
    val current: Current,
    val daily: List<Daily>
)

data class Current(val dt: Long, val temp: Double, val weather: List<Weather>)
data class Daily(val dt: Long, val temp: Temp, val weather: List<Weather>)
data class Temp(val day: Double)
data class Weather(val main: String, val description: String)
