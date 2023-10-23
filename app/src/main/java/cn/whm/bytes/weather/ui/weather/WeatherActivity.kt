package cn.whm.bytes.weather.ui.weather

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import cn.whm.bytes.weather.BR
import cn.whm.bytes.weather.R
import cn.whm.bytes.weather.databinding.ActivityWeatherBinding
import cn.whm.bytes.weather.logic.model.Weather
import cn.whm.bytes.weather.logic.model.getSky
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import java.text.SimpleDateFormat
import java.util.Locale


class WeatherActivity : BaseActivity() {

    val viewModel: WeatherViewModel by lazy {
        getActivityScopeViewModel(WeatherViewModel::class.java)
    }

    val viewBinding: ActivityWeatherBinding by lazy {
        binding as ActivityWeatherBinding
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            R.layout.activity_weather,
            BR.vm,
            viewModel
        )
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            viewBinding.swipeRefresh.isRefreshing = false
        }
        viewBinding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        viewBinding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        viewBinding.now.navBtn.setOnClickListener {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        viewBinding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        viewBinding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        viewBinding.now.placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        viewBinding.now.currentTemp.text = currentTempText
        viewBinding.now.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        viewBinding.now.currentAQI.text = currentPM25Text
        viewBinding.now.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        viewBinding.forecast.forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this)
                .inflate(R.layout.forecast_item, viewBinding.forecast.forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            viewBinding.forecast.forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        viewBinding.lifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        viewBinding.lifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        viewBinding.lifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        viewBinding.lifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
        viewBinding.weatherLayout.visibility = View.VISIBLE
    }

}
