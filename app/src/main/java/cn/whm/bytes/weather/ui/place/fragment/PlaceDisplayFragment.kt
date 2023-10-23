package cn.whm.bytes.weather.ui.place.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.whm.bytes.weather.BR
import cn.whm.bytes.weather.MainActivity
import cn.whm.bytes.weather.R
import cn.whm.bytes.weather.SWApplication
import cn.whm.bytes.weather.databinding.FragmentPlaceBinding
import cn.whm.bytes.weather.ui.place.adapter.PlaceDisplayAdapter
import cn.whm.bytes.weather.ui.place.model.PlaceViewModel
import cn.whm.bytes.weather.ui.weather.WeatherActivity
import cn.whm.bytes.weather.utils.spread_function.showToast
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig

/**
 * Created by juwuguo on 2020-04-21.
 */
class PlaceDisplayFragment : BaseFragment() {

    private val viewBinding: FragmentPlaceBinding by lazy {
      binding as FragmentPlaceBinding
    }

    val viewModel: PlaceViewModel by lazy {
        getFragmentScopeViewModel(PlaceViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            R.layout.fragment_place,
            BR.vm,
            viewModel
        )
    }

    private lateinit var adapter: PlaceDisplayAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        viewBinding.rvDisplayPlace.layoutManager = layoutManager
        adapter = PlaceDisplayAdapter(this, viewModel.placeList)
        viewBinding.rvDisplayPlace.adapter = adapter

        viewBinding.etSearchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                viewBinding.rvDisplayPlace.visibility = View.GONE
                viewBinding.ivPlaceBg.visibility = View.GONE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewBinding.rvDisplayPlace.visibility = View.VISIBLE
                viewBinding.ivPlaceBg.visibility = View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                R.string.no_place_search.showToast(SWApplication.mContext)
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}