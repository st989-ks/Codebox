package com.pipe.codebox.presenter.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pipe.codebox.presenter.entity.PositionTransport
import com.pipe.codebox.extensions.*
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.properties.Delegates


class TransportRouteViewModel @Inject constructor() : ViewModel() {

    private val moveLiveData = MutableLiveData<PositionTransport>()

    private val viewModelScopeCoroutine = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            Log.i(APP_TAG,throwable.toString())
        }
    )

    fun getMoveLiveData() = moveLiveData

    fun stopTransport() {
        viewModelScopeCoroutine.launch {
            viewModelScopeCoroutine.coroutineContext.cancelChildren()
        }
    }

    fun moveTransport(points: List<Point>) {
        viewModelScopeCoroutine.launch {
            move(points)
        }
    }

    private fun move(points: List<Point>): Job =
        viewModelScopeCoroutine.launch {
            startMoveTransport(points)
        }

    private suspend fun startMoveTransport(points: List<Point>) {
        var isFirstPoint = true
        var previousPoint = Point()
        var angle: Double = ZERO_VALUE
        var distance: Double = ZERO_VALUE
        var fullDist: Double = ZERO_VALUE
        points.forEach { currentPoint ->

            if (isFirstPoint.not()) {
                delay(DELAY_MOVE)
                angle = previousPoint.calculateAngle(currentPoint)
                distance = previousPoint.calculateTheDistance(currentPoint)
                fullDist += distance
                if (distance > MIN_DISTANCE_DRIVE) {
                    getMorePoints(currentPoint, previousPoint, distance, angle, fullDist)
                } else {
                    getMoveLiveData().postValue(
                        PositionTransport(currentPoint, angle, distance, fullDist)
                    )
                }
            }
            previousPoint = currentPoint
            isFirstPoint = false
        }
        delay(DELAY_MOVE)
        getMoveLiveData().postValue(
            PositionTransport(previousPoint, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE, true)
        )
    }

    private suspend fun getMorePoints(
        currentPoint: Point,
        previousPoint: Point,
        distance: Double,
        angle: Double,
        fullDist: Double
    ) {
        val dx = currentPoint.latitude - previousPoint.latitude
        val dy = currentPoint.longitude - previousPoint.longitude
        var latitude by Delegates.notNull<Double>()
        var longitude by Delegates.notNull<Double>()
        for (i in 0..distance.roundToInt()) {
            latitude = previousPoint.latitude.plus((dx * i / distance))
            longitude = previousPoint.longitude.plus((dy * i / distance))
            delay(DELAY_MOVE)
            getMoveLiveData().postValue(
                PositionTransport(Point(latitude, longitude), angle, distance, fullDist)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScopeCoroutine
            .coroutineContext
            .cancel()
    }
}
