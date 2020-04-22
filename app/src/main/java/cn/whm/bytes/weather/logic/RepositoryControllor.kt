package cn.whm.bytes.weather.logic

import androidx.lifecycle.liveData
import cn.whm.bytes.weather.logic.model.PlaceBean
import cn.whm.bytes.weather.logic.network.WeatherNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 * Created by juwuguo on 2020-04-21.
 */
object RepositoryControllor {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<PlaceBean>>(e)
        }
        emit(result)
    }
}