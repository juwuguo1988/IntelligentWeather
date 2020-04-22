package cn.whm.bytes.weather.logic.dao

import android.content.Context
import androidx.core.content.edit
import cn.whm.bytes.weather.SWApplication
import cn.whm.bytes.weather.logic.model.PlaceBean
import com.google.gson.Gson

object PlaceHistoryDao {

    fun savePlace(place: PlaceBean) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): PlaceBean {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, PlaceBean::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SWApplication.mContext.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}