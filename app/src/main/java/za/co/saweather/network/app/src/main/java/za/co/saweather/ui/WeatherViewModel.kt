package za.co.saweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import za.co.saweather.repo.WeatherRepository

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {
    val data = MutableLiveData<String?>()
    fun load(lat: Double, lon: Double, cityId: String) {
        viewModelScope.launch { data.value = repo.fetchAndCache(lat, lon, cityId) }
    }
}
