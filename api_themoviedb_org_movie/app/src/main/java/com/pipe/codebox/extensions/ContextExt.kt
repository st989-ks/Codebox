package com.pipe.codebox.extensions

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.R
import com.pipe.codebox.extensions.recyclerAdapter.BaseViewHolder
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

var dialog: ProgressDialog? = null
fun Context.showLoader(isShow: Boolean) {
    if (isShow) {
        dialog = ProgressDialog(this)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.show()
        dialog?.setContentView(R.layout.progress_bar)
    } else {
        dialog?.dismiss()
        dialog = null
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("UseCompatLoadingForDrawables")
fun Context.showToast(id: Int): View =
    View(this).apply { background = this.context.getDrawable(id) }


fun ImageView.downloadImage(path: String?) {
    var `in`: InputStream? = null
    var bmp: Bitmap? = null
    var responseCode = -1
    try {
        val url = URL(path)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.doInput = true
        con.connect()
        responseCode = con.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //download
            `in` = con.inputStream
            bmp = BitmapFactory.decodeStream(`in`)
            `in`.close()
            this.setImageBitmap(bmp)
        }
    } catch (ex: Exception) {
        Log.e("Exception", ex.toString())
    }
}

fun Context.isInternetAvailable(): Boolean {
    var result = false
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            result = when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }

        }
    }
    return result
}


