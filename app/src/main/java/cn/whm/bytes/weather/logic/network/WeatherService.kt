package cn.whm.bytes.weather.logic.network

import cn.whm.bytes.weather.SWApplication
import cn.whm.bytes.weather.logic.model.DailyResponse
import cn.whm.bytes.weather.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SWApplication.app_token}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealTimeResponse>

    @GET("v2.5/${SWApplication.app_token}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}