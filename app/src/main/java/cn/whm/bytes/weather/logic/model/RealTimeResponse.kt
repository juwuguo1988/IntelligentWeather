package cn.whm.bytes.weather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by juwuguo on 2020-04-22.
 */
class RealTimeResponse(val status: String, val result: Result) {

    class Result(val realtime: RealTime)

    class RealTime(
        val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    class AirQuality(val aqi: AQI)

    class AQI(val chn: Float)
}