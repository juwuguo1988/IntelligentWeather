package cn.whm.bytes.weather.logic.network

import cn.whm.bytes.weather.SWApplication
import cn.whm.bytes.weather.logic.model.PlaceResponseBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by juwuguo on 2020-04-21.
 */
interface PlaceService {

    @GET("v2/place?token=${SWApplication.app_token}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponseBean>
}