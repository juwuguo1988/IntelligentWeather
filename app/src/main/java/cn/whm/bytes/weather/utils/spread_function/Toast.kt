package cn.whm.bytes.weather.utils.spread_function

import android.content.Context
import android.widget.Toast

/**
 * Created by juwuguo on 2020-04-21.
 */

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, this, duration).show()
}


fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, this, duration).show()
}