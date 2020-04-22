package cn.whm.bytes.weather.logic

import androidx.lifecycle.liveData
import cn.whm.bytes.weather.logic.dao.PlaceHistoryDao
import cn.whm.bytes.weather.logic.model.PlaceBean
import cn.whm.bytes.weather.logic.model.Weather
import cn.whm.bytes.weather.logic.network.WeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * Created by juwuguo on 2020-04-21.
 */
object RepositoryControllor {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherNetWork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }


    fun refreshWeather(lng: String, lat: String, placeName: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                WeatherNetWork.getRealTimeWeather(lng, lat)
            }
            val deferredDaily = async {
                WeatherNetWork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }


    fun savePlace(place: PlaceBean) = PlaceHistoryDao.savePlace(place)

    fun getSavedPlace() = PlaceHistoryDao.getSavedPlace()

    fun isPlaceSaved() = PlaceHistoryDao.isPlaceSaved()
}