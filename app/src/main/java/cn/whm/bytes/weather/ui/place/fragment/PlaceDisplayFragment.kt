package cn.whm.bytes.weather.ui.place.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.whm.bytes.weather.R
import cn.whm.bytes.weather.SWApplication
import cn.whm.bytes.weather.ui.place.adapter.PlaceDisplayAdater
import cn.whm.bytes.weather.ui.place.model.PlaceViewModel
import cn.whm.bytes.weather.utils.spread_function.showToast
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * Created by juwuguo on 2020-04-21.
 */
class PlaceDisplayFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    private lateinit var adapter: PlaceDisplayAdater


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        rv_display_place.layoutManager = layoutManager
        adapter = PlaceDisplayAdater(this, viewModel.placeList)
        rv_display_place.adapter = adapter

        et_search_place_edit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                rv_display_place.visibility = View.GONE
                iv_place_bg.visibility = View.GONE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                rv_display_place.visibility = View.VISIBLE
                iv_place_bg.visibility = View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                R.string.no_place_search.showToast(SWApplication.mContext)
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}