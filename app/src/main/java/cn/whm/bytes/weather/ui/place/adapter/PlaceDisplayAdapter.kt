package cn.whm.bytes.weather.ui.place.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.whm.bytes.weather.R
import cn.whm.bytes.weather.logic.model.PlaceBean
import cn.whm.bytes.weather.ui.place.fragment.PlaceDisplayFragment
import cn.whm.bytes.weather.ui.weather.WeatherActivity

/**
 * Created by juwuguo on 2020-04-21.
 */
class PlaceDisplayAdapter(
    private val fragment: PlaceDisplayFragment,
    private val placeList: List<PlaceBean>
) :
    RecyclerView.Adapter<PlaceDisplayAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_place_name: TextView = view.findViewById(R.id.tv_place_name)
        val tv_place_address: TextView = view.findViewById(R.id.tv_place_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.viewBinding.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.tv_place_name.text = place.name
        holder.tv_place_address.text = place.address
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

}