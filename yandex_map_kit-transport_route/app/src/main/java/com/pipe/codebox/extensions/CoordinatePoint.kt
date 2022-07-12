package com.pipe.codebox.extensions

import com.yandex.mapkit.geometry.Point
import kotlin.math.*

/**
 * https://www.kobzarev.com/programming/calculation-of-distances-between-cities-on-their-coordinates/
 * Вычисление дистанции
 */

fun Point.calculateTheDistance(endPoint: Point): Double {
    try {
        //переводим координаты в радианы, находим
        //косинусы и синусы широт и разницы долгот
        val cosLatitudeFirst = cos(latitudeInRadian(this))
        val sinLatitudeFirst = sin(latitudeInRadian(this))
        val cosLatitudeSecond = cos(latitudeInRadian(endPoint))
        val sinLatitudeSecond = sin(latitudeInRadian(endPoint))

        val delta = longitudeInRadian(endPoint) - longitudeInRadian(this)
        val cosDelta = cos(delta)
        val sinDelta = sin(delta)

        //вычисления длины большого круга
        val y = sqrt(
            (cosLatitudeSecond * sinDelta).pow(2)
                    + (cosLatitudeFirst * sinLatitudeSecond -
                    sinLatitudeFirst * cosLatitudeSecond * cosDelta).pow(2)
        )
        val x = sinLatitudeFirst * sinLatitudeSecond +
                cosLatitudeFirst * cosLatitudeSecond * cosDelta

        val arcTan = atan2(y, x)
        val distance = arcTan * radiusEarth
        if (distance.isNaN()) return 0.0
        return distance
    } catch (err: Exception) {
        return 0.0
    }
}


/**
 * https://gis-lab.info/qa/great-circles.html
 * реализация на Avenue
 * вычисление направления объекта
 */

fun Point.calculateAngle(endPoint: Point): Double {
    try {
        //переводим координаты в радианы, находим
        //косинусы и синусы широт и разницы долгот
        val cosLatitudeFirst = cos(latitudeInRadian(this))
        val sinLatitudeFirst = sin(latitudeInRadian(this))
        val cosLatitudeSecond = cos(latitudeInRadian(endPoint))
        val sinLatitudeSecond = sin(latitudeInRadian(endPoint))

        val delta = longitudeInRadian(endPoint) - longitudeInRadian(this)
        val cosDelta = cos(delta)
        val sinDelta = sin(delta)

        //вычисление начального азимута
        val x = (cosLatitudeFirst * sinLatitudeSecond) -
                (sinLatitudeFirst * cosLatitudeSecond * cosDelta)
        val y = sinDelta * cosLatitudeSecond
        var z = Math.toDegrees(atan(-y / x))

        if (x < 0) z += angle

        var z2 = (z + angle) % 360 - angle
        z2 = -Math.toRadians(z2)
        val angleRad = z2 - ((2 * pi) * floor((z2 / (2 * pi))))
        val angleDeg = (angleRad * angle) / pi
        if (angleDeg.isNaN()) return 0.0
        return angleDeg
    } catch (err: Exception) {
        return 0.0
    }
}

private fun longitudeInRadian(point: Point) = point.longitude * pi / angle

private fun latitudeInRadian(point: Point) = point.latitude * pi / angle

const val angle = 180
const val pi = Math.PI
const val radiusEarth = 6372795