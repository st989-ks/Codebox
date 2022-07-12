package com.pipe.codebox.extensions

const val APP_TAG = "app_tag"
const val ERROR_TAG = "Error in this app"

fun MutableList<Int>.findClosest(input: Int) = fold(null as Int?) { acc, num ->
    val closest = if (num <= input && (acc == null || num > acc)) num else acc
    if (closest == input) return closest else closest
}

