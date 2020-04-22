package cn.whm.bytes.weather.logic.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.logging.Level
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by juwuguo on 2020-04-21.
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"


    private val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        .setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        .setColorLevel(Level.WARNING)

    private val builder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(builder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 正常写法:
     * val appService  = retrofit.create(AppService::class.java)
     * 第一步:
     * fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
     * 使用的时候
     * val appService  = ServiceCreator.create(AppService::class.java)
     * 第二步:
     * 使用泛型实化:inline关键字修饰方法，用reified修饰泛型
     * inline fun <reified T> create(): T = create(T::class.java)
     *
     */

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}