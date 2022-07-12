package com.pipe.codebox.extensions

import android.content.Context
import android.content.SharedPreferences
import com.pipe.codebox.BuildConfig


class SharedPrefs(context: Context) {

    companion object {
        private const val PREF = BuildConfig.APPLICATION_ID
        private const val POINT_LATITUDE = "point_latitude"
        private const val POINT_LONGITUDE = "point_longitude"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)


//    fun setPoint(point: Point) {
//        put(POINT_LATITUDE, point.latitude)
//        put(POINT_LONGITUDE, point.longitude)
//    }
//
//    fun getPoint() = Point(
//        get(POINT_LATITUDE, Double::class.java),
//        get(POINT_LONGITUDE, Double::class.java)
//    )
//
//    fun cleanPoint() {
//        setPoint(Point(0.0,0.0))
//    }

    private fun <T> get(key: String, clazz: Class<T>) =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, 0.0f)
            Double::class.java -> sharedPref.getFloat(key, 0.0f)
            Int::class.java -> sharedPref.getInt(key, 0)
            Long::class.java -> sharedPref.getLong(key, 0)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()

        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)

        }
        editor.apply()
    }
}
