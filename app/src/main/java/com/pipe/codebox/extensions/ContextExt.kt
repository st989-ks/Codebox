package com.pipe.codebox.extensions

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import com.pipe.codebox.R

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