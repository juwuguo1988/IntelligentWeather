package cn.whm.bytes.weather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by juwuguo on 2020-04-21.
 */
object WeatherNetWork {
    // private val placeService = ServiceCreator.create(PlaceService::class.java)
    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    /**
     * 参考473页
     * 声明一个泛型T，并将await()函数定义成Call<T>的扩展函数，
     * 这样所有返回值是Call类型的Retrofit网络请求接口就可以直接调用await()函数
     *
     * 在await函数中，首先调用suspendCoroutine挂起当前协程，并且由于扩展函数的原因
     * 可以直接调用enqueue()让Retrofit发起网络请求。
     * 接下来，使用同样的方式，对Retrofit响应的数据，或者网络请求失败的情况做处理。
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}