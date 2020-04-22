package cn.whm.bytes.weather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.whm.bytes.weather.logic.RepositoryControllor
import cn.whm.bytes.weather.logic.model.LocationBean

/**
 * Created by juwuguo on 2020-04-22.
 */
class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<LocationBean>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        RepositoryControllor.refreshWeather(location.lng, location.lat, placeName)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = LocationBean(lng, lat)
    }

}