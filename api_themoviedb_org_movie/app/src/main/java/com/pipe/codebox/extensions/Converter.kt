package com.pipe.codebox.extensions

import java.math.RoundingMode

fun String.voteAverageGetFiveAsString(): Double =
    (this.toDouble() * 5 / 10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

fun String.replacingSpaces(): String {
    return  this.replace(",+.".toRegex(), " ").replace("\\s".toRegex(), "+")
}

fun Double.voteAverageGetFiveAsDouble(): Double =
    (this * 5 / 10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()




