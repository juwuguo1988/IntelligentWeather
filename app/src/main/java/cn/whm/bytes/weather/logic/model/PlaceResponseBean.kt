package cn.whm.bytes.weather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by juwuguo on 2020-04-21.
 */

data class PlaceResponseBean(val status: String, val places: List<PlaceBean>)

data class PlaceBean(
    val name: String,
    val location: LocationBean, @SerializedName("formatted_address") val address: String
)

data class LocationBean(val lng: String, val lat: String)