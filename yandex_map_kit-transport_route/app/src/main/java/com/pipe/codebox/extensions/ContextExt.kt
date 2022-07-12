package com.pipe.codebox.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.pipe.codebox.R

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("UseCompatLoadingForDrawables")
fun Context.showPoint(id:Int) :View = View(this).apply { background = this.context.getDrawable(id)}
