package com.pipe.codebox.ui.fragment.route

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.pipe.codebox.R
import com.pipe.codebox.extensions.*
import com.pipe.codebox.ui.fragment.base.BaseFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_map.*

abstract class AbstractTransportRouteFragment : BaseFragment(),
    DrivingSession.DrivingRouteListener, CameraListener, UserLocationObjectListener {

    protected var isFABOpen = false
    protected var permissionLocation = false
    protected var followUserLocation = false
    private lateinit var locationLayer: UserLocationLayer
    protected var mapObjectsCollection: MapObjectCollection? = null
    private var routeStartLocation: Point = Point(POINT_X, POINT_Y)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            Log.d(APP_TAG, getString(R.string.permission_ok))
        } else {
            Log.d(APP_TAG, getString(R.string.permission_no))
        }
    }

    protected fun firstInitStart() {
        checkPermission()
        initMapKit()
    }

    protected fun getMapObject(point: Point, idDrawable: Int): PlacemarkMapObject {
        return mapObjectsCollection!!.addPlacemark(
            point,
            ViewProvider(requireActivity().showPoint(idDrawable))
        )
    }

    protected fun getPositionCamera(
        point: Point = routeStartLocation,
        animationF: Float = ANIMATION_FLOAT,
        zoom: Float = ZOOM
    ) {
        map_view.map.move(
            CameraPosition(point, zoom, POSITION_FLOAT, POSITION_FLOAT),
            Animation(Animation.Type.SMOOTH, animationF),
            null
        )
    }

    protected fun cameraUserPosition() {
        if (locationLayer.cameraPosition() != null) {
            routeStartLocation = locationLayer.cameraPosition()!!.target
            sharedPrefs.setPoint(routeStartLocation)
            getPositionCamera(routeStartLocation)
        } else {
            getPositionCamera(routeStartLocation)
        }
    }

    private fun initMapKit() {
        routeStartLocation = sharedPrefs.getPoint()
        val mapKit = MapKitFactory.getInstance()
        locationLayer = mapKit.createUserLocationLayer(map_view.mapWindow)
        locationLayer.isVisible = true
        locationLayer.isHeadingEnabled = true
        locationLayer.setObjectListener(this)
        map_view.map.addCameraListener(this)
        permissionLocation = true
        mapObjectsCollection = map_view.map.mapObjects
        getPositionCamera(zoom = 12f)
    }

    protected fun checkPermission() {
        if (!isPermissionGranted()) {
            permissionLocation = true
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {
        if (p3 && followUserLocation) {
            setAnchor()
        } else if (!followUserLocation) {
            noAnchor()
        }
    }

    override fun onObjectAdded(p0: UserLocationView) {
        setAnchor()
        p0.pin.setIcon(ImageProvider.fromResource(requireActivity(), R.drawable.user_arrow))
        p0.arrow.setIcon(ImageProvider.fromResource(requireActivity(), R.drawable.user_arrow))
        p0.accuracyCircle.fillColor = Color.TRANSPARENT
    }

    private fun setAnchor() {
        user_location_fab.setImageResource(R.drawable.ic_location_found)
        followUserLocation = false
    }

    private fun noAnchor() {
        locationLayer.resetAnchor()
        user_location_fab.setImageResource(R.drawable.ic_location_search)
    }

    private fun isPermissionGranted(): Boolean =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    override fun onDrivingRoutesError(p0: Error) {}

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        map_view.onStart()
    }

    override fun onStop() {
        map_view.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    protected fun closeFABMenu() {
        isFABOpen = false
        user_location_fab.animate().rotation(0F)

        fab_location.animate().translationY(resources.getDimension(R.dimen.standard_position))
        fab_transport.animate().translationY(resources.getDimension(R.dimen.standard_position))
        fab_manual.animate().translationX(resources.getDimension(R.dimen.standard_position))
        fab_location.visibility = View.INVISIBLE
        fab_transport.visibility = View.INVISIBLE
        fab_manual.visibility = View.INVISIBLE
    }

    protected fun showFABMenu() {
        isFABOpen = true
        fab_location.visibility = View.VISIBLE
        fab_transport.visibility = View.VISIBLE
        fab_manual.visibility = View.VISIBLE

        user_location_fab.animate().rotationBy(resources.getDimension(R.dimen.standard_rotation))
        fab_location.animate().translationY(-resources.getDimension(R.dimen.standard_btn_location))
        fab_transport.animate().translationY(-resources.getDimension(R.dimen.standard_btn_transport))
        fab_manual.animate().translationX(-resources.getDimension(R.dimen.standard_btn_fab_manual))
    }

    override fun onObjectRemoved(userLocationView: UserLocationView) {}

    override fun onObjectUpdated(userLocationView: UserLocationView, event: ObjectEvent) {}

}