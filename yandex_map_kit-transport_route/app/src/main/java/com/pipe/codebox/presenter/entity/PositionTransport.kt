package com.pipe.codebox.presenter.entity

import com.yandex.mapkit.geometry.Point

data class PositionTransport(
    val position: Point,
    val angle: Double,
    val dist: Double,
    val fullDist: Double,
    val isFinish: Boolean = false
)
