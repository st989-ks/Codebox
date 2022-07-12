package com.pipe.codebox.domain.entity

import com.yandex.mapkit.geometry.Point

data class Locations(
    val keyPoint: Int? = null,
    val markerId: Int = 0,
    val name: String = "",
    val point: Point
)
