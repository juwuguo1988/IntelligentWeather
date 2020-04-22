package cn.whm.bytes.weather.ui.place.model

import android.app.DownloadManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.whm.bytes.weather.logic.RepositoryControllor
import cn.whm.bytes.weather.logic.model.PlaceBean

/**
 * Created by juwuguo on 2020-04-21.
 */
class PlaceViewModel : ViewModel() {
    /**
     * liveData是JetPack提供的一种响应式编程组件，它可以包含任何类型数据，可以再数据发生变化的时候
     * 通知给观察者。LiveData特别合适与ViewModel一起使用
     *
     *
     * 工作流程:当调用PlaceViewModel的searchPlaces()方法获取数据时，并不会发生网络请求
     * 只是简单的将query值设置到searchLiveData中，一旦searchLiveData的数据发生变化
     * 那么观察searchLiveData的switchMap()方法就会执行，并且调用我们编写的转换函数，
     * 转换函数里调用 RepositoryControllor.searchPlaces(query)方法获取真正数据，
     * 并且switchMap()将上面返回的LiveData对象转换成一个可观察的liveData对象
     * 对于Activity而言，只要观察这个LiveData对象就可以了。
     *
     */

    private val searchLiveData = MutableLiveData<String>()

    /**
     * 用于缓存Activity、fragment界面上的数据
     * 原则上，与界面相关的数据都应该放在ViewModel中
     */
    val placeList = ArrayList<PlaceBean>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        RepositoryControllor.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }


    fun savePlace(place: PlaceBean) = RepositoryControllor.savePlace(place)

    fun getSavedPlace() = RepositoryControllor.getSavedPlace()

    fun isPlaceSaved() = RepositoryControllor.isPlaceSaved()

}