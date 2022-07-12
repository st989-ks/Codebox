package com.pipe.codebox.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("UseCompatLoadingForDrawables")
fun Context.showToast(id: Int): View =
    View(this).apply { background = this.context.getDrawable(id) }
