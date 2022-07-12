package com.pipe.codebox.ui.fragment.map

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.extensions.*
import com.pipe.codebox.ui.fragment.SavePointDialog
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.logo.HorizontalAlignment.LEFT
import com.yandex.mapkit.logo.VerticalAlignment.BOTTOM
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.AnimatedImageProvider
import com.yandex.runtime.image.ImageProvider.fromResource
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_location_points.*
import kotlinx.android.synthetic.main.fragment_map.*


@AndroidEntryPoint
class MapMapFragment : AbstractMapFragment(), CameraListener {

    override val layoutId: Int = R.layout.fragment_map

    private lateinit var locationLayer: UserLocationLayer

    private lateinit var mapObjects: MapObjectCollection

    private var routeStartLocation: Point = Point(POINT_X, POINT_Y)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        initMapKit()
        initButton()
        userInterface()
        shared()
        observer()
    }

    private fun observer() {
        gudLocationPointsViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        gudLocationPointsViewModel.startGetList()
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is List<*> -> {
                val loc: List<Locations> = data as List<Locations>
                updateMark(loc)
            }
        }
    }

    private fun updateMark(loc: List<Locations>){
        loc.forEach { it ->
            mapObjects.addPlacemark(
                it.point,
                ViewProvider(requireActivity().showToast(it.markerId))
            )
            mapObjects.userData = it.name
        }
    }

    private fun shared() {
        val shared = sharedPrefs.getPoint()
        if (shared.latitude > 1) {
            getPosition(shared)
            sharedPrefs.cleanPoint()
        }
    }

    private fun initMapKit() {
        val mapKit = MapKitFactory.getInstance()
        locationLayer = mapKit.createUserLocationLayer(mapview.mapWindow)
        locationLayer.isVisible = true
        locationLayer.isHeadingEnabled = true
        locationLayer.setObjectListener(this)
        mapview.map.addCameraListener(this)
        permissionLocation = true
        mapObjects = mapview.map.mapObjects
    }

    private fun userInterface() {
        val mapLogoAlignment = Alignment(LEFT, BOTTOM)
        mapview.map.logo.setAlignment(mapLogoAlignment)
    }

    private fun cameraUserPosition() {
        if (locationLayer.cameraPosition() != null) {
            routeStartLocation = locationLayer.cameraPosition()!!.target
            getPosition(routeStartLocation)
        } else {
            getPosition(routeStartLocation)
        }
    }

    private fun initButton() {
        user_location_fab.setOnClickListener { followToLocate() }
        btn_display_list.setOnClickListener { navigatorModule.navigate(R.id.locationPointsFragment) }
        mapview.map.addInputListener(inputListener)
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        setAnchor()
        userLocationView.pin.setIcon(fromResource(requireActivity(), R.drawable.user_arrow))
        userLocationView.arrow.setIcon(fromResource(requireActivity(), R.drawable.user_arrow))
        userLocationView.accuracyCircle.fillColor = TRANSPARENT
    }

    private fun followToLocate() {
        if (permissionLocation) {
            cameraUserPosition()
            followUserLocation = true
        } else {
            checkPermission()
        }
    }

    private fun getPosition(point: Point = routeStartLocation) {
        mapview.map.move(
            CameraPosition(point, ZOOM, ZERO_FLOAT, ZERO_FLOAT),
            Animation(Animation.Type.SMOOTH, ZERO_FLOAT),
            null
        )
    }

    private fun setAnchor() {
        user_location_fab.setImageResource(R.drawable.ic_location_found)
        followUserLocation = false
    }

    private fun noAnchor() {
        locationLayer.resetAnchor()
        user_location_fab.setImageResource(R.drawable.ic_location_search)
    }


    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        finish: Boolean
    ) {
        if (finish && followUserLocation) {
            setAnchor()
        } else if (!followUserLocation) {
            noAnchor()
        }
    }

    private val inputListener: InputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            Log.i(APP_TAG, "${point.latitude} x ${point.longitude}")
        }

        override fun onMapLongTap(map: Map, point: Point) {
            openSavePointDialog(Locations(point = Point(point.latitude, point.longitude)))
        }
    }

    private fun openSavePointDialog(location: Locations) {
        SavePointDialog(requireActivity(), location = location).apply {
            show()
            onSave = { loc ->
                saveLocationPointsViewModel.startSave(loc)
                dismiss()
                gudLocationPointsViewModel.startGetList()
            }
        }
    }
}