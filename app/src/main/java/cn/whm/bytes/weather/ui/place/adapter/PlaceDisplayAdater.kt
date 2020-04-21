package cn.whm.bytes.weather.ui.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import cn.whm.bytes.weather.R
import cn.whm.bytes.weather.logic.model.PlaceBean

/**
 * Created by juwuguo on 2020-04-21.
 */
class PlaceDisplayAdater(private val fragment: Fragment, private val placeList: List<PlaceBean>) :
    RecyclerView.Adapter<PlaceDisplayAdater.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_place_name: TextView = view.findViewById(R.id.tv_place_name)
        val tv_place_address: TextView = view.findViewById(R.id.tv_place_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
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