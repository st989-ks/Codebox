package com.pipe.codebox.extensions

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.pipe.codebox.R

fun ColorEnum.getColorRes() : Int = when (this) {
        ColorEnum.LIGHT_GREEN -> R.color.color_light_green
        ColorEnum.DARK_GREEN -> R.color.color_dark_green
        ColorEnum.ORANGE -> R.color.color_orange
        ColorEnum.YELLOW -> R.color.color_yellow
        ColorEnum.GREEN -> R.color.color_green
        ColorEnum.BLUE -> R.color.color_blue
        ColorEnum.RED -> R.color.color_red
    }

    fun ColorEnum.getColorInt(context : Context) = ResourcesCompat.getColor(context.resources, getColorRes(), null)
