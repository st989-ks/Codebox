package com.pipe.codebox.ui.fragment.route

import android.os.Bundle
import android.util.Log
import android.view.View
import com.pipe.codebox.R
import com.pipe.codebox.presenter.entity.PositionTransport
import com.pipe.codebox.extensions.*
import com.pipe.codebox.presenter.view.TransportRouteViewModel
import com.pipe.codebox.ui.fragment.SavePointDialog
import com.yandex.mapkit.*
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.*
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

@AndroidEntryPoint
class TransportRouteFragment : AbstractTransportRouteFragment() {

    override val layoutId: Int = R.layout.fragment_map

    @Inject
    lateinit var transportRouteViewModel: TransportRouteViewModel

    private var path: DrivingRoute? = null
    private var drivingRouter: DrivingRouter? = null
    private var drivingSession: DrivingSession? = null

    private var currentRouter: PolylineMapObject? = null
    private var transport: PlacemarkMapObject? = null
    private var finishWay: PlacemarkMapObject? = null

    private var longClickChange = false
    private var movie = false

    private val inputListener: InputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            shortTapMap(map, point)
        }

        override fun onMapLongTap(map: Map, point: Point) {
            longTapMap(map, point)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstInitStart()
        observe()
        initListener()
        openDialog()
    }

    private fun observe() {
        transportRouteViewModel.getMoveLiveData()
            .observe(viewLifecycleOwner) { res -> renderCarMove(result = res) }
    }

    private fun initListener() {
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        user_location_fab.setOnClickListener { if (!isFABOpen) showFABMenu() else closeFABMenu() }
        fab_location.setOnClickListener { followToLocate() }
        fab_transport.setOnClickListener { cornerMove() }
        fab_manual.setOnClickListener { openDialog() }
        map_view.map.addInputListener(inputListener)
    }

    private fun followToLocate() {
        setNullMarkStartFinish()
        if (permissionLocation) {
            cameraUserPosition()
            followUserLocation = true
        } else {
            checkPermission()
        }
    }

    private fun renderCarMove(result: PositionTransport?) {
        if (result != null) {
            if (checkFinish(result)) return

            transport?.geometry = result.position
            val iconStyle = IconStyle(
                null, RotationType.ROTATE, null, null,
                null, null, null
            )
            transport?.setIconStyle(iconStyle)
            transport?.direction = result.angle.toFloat()

            getPositionCamera(point = result.position, animationF = 0f)
        }
    }

    private fun shortTapMap(map: Map, point: Point) {
        Log.i(APP_TAG, "${point.latitude} x ${point.longitude}")
    }

    private fun setMarkTransport(point: Point) {
        setNullMarkStartFinish()
        transport = getMapObject(point, R.drawable.ic_transport)
        transport?.isVisible
        longClickChange = true
    }

    private fun setMarkFinishWay(point: Point) {
        finishWay = getMapObject(point, R.drawable.ic_finish)
        finishWay?.isVisible
        longClickChange = false
    }

    private fun setNullMarkStartFinish() {
        stopMovie()
        mapObjectsCollection?.clear()
        transport = null
        finishWay = null
        longClickChange = false
    }

    private fun longTapMap(map: Map, point: Point) {
        if (!longClickChange && transport == null) {
            setMarkTransport(point)
            return
        } else if (longClickChange && finishWay == null) {
            setMarkFinishWay(point)
            drivingSession =
                drivingRouter?.requestRoutes(
                    getRequestPoints(),
                    DrivingOptions(),
                    VehicleOptions(),
                    this
                )
        } else {
            setMarkTransport(point)
            return
        }
    }

    private fun cornerMove() {
        setNullMarkStartFinish()

        val startPoint = ScreenPoint(
            MIN_DISTANCE_POINT.toFloat(),
            MIN_DISTANCE_POINT.toFloat()
        )
        val endPoint = ScreenPoint(
            (map_view.width() - MIN_DISTANCE_POINT).toFloat(),
            (map_view.height() - MIN_DISTANCE_POINT).toFloat()
        )

        setMarkTransport(map_view.screenToWorld(startPoint))
        setMarkFinishWay(map_view.screenToWorld(endPoint))

        drivingSession =
            drivingRouter?.requestRoutes(
                getRequestPoints(),
                DrivingOptions(),
                VehicleOptions(),
                this
            )
    }

    private fun checkFinish(result: PositionTransport): Boolean {
        return if (result.isFinish) {
            movie = false
            getPositionCamera(result.position)
            true
        } else {
            false
        }
    }

    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        path = p0.firstOrNull()
        path?.let {
            currentRouter = mapObjectsCollection?.addPolyline(it.geometry)
        }
        setTransportClickListener()
    }

    private fun setTransportClickListener() {
        transport?.addTapListener { _, _ ->
            if (movie) return@addTapListener false
            path?.geometry?.points?.let {
                movie = true
                transportRouteViewModel.moveTransport(it)
            }
            true
        }
    }

    private fun stopMovie() {
        if (movie) {
            transportRouteViewModel.stopTransport()
            movie = false
        }
    }

    private fun getRequestPoints(): ArrayList<RequestPoint> {
        val requestPoints = ArrayList<RequestPoint>()
        requestPoints.add(RequestPoint(transport!!.geometry, RequestPointType.WAYPOINT, null))
        requestPoints.add(RequestPoint(finishWay!!.geometry, RequestPointType.WAYPOINT, null))
        return requestPoints
    }

    private fun openDialog() {
        SavePointDialog(requireActivity(), getString(R.string.manual_text)).apply {
            show()
            onOk = {
                dismiss()
            }
        }
    }
}