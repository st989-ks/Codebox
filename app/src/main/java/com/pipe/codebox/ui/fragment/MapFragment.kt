package com.pipe.codebox.ui.fragment

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.graphics.Color.BLUE
import android.graphics.Color.TRANSPARENT
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.pipe.codebox.R
import com.pipe.codebox.di.NavigatorModule
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.ZERO_FLOAT
import com.pipe.codebox.extensions.ZOOM
import com.pipe.codebox.presenter.view.LocationPointsViewModel
import com.pipe.codebox.presenter.view.MapViewModel
import com.pipe.codebox.presenter.view.SharedViewModel
import com.pipe.codebox.ui.adapter.LocationPointsAdapter
import com.pipe.codebox.ui.fragment.base.BaseFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.logo.HorizontalAlignment.LEFT
import com.yandex.mapkit.logo.VerticalAlignment.BOTTOM
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider.fromResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : BaseFragment(), UserLocationObjectListener, CameraListener {

    override val layoutId: Int = R.layout.fragment_map

    @Inject
    lateinit var locationPointsAdapter: LocationPointsAdapter

    @Inject
    lateinit var locationPointsViewModel: LocationPointsViewModel

    @Inject
    lateinit var mapViewModel: MapViewModel

    private lateinit var locationLayer: UserLocationLayer

    private var routeStartLocation: Point = Point(59.9738, 30.2213)

    private var permissionLocation = false
    private var followUserLocation = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            Log.i(APP_TAG, getString(R.string.permition_ok))
        } else {
            Log.i(APP_TAG, getString(R.string.permition_no))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        observe()
        initMapKit()
        initButton()
        userInterface()
    }

    private fun initMapKit() {
        val mapKit = MapKitFactory.getInstance()
        locationLayer = mapKit.createUserLocationLayer(mapview.mapWindow)
        locationLayer.isVisible = true
        locationLayer.isHeadingEnabled = true
        locationLayer.setObjectListener(this)
        mapview.map.addCameraListener(this)
        permissionLocation = true
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
        locationPointsAdapter.setOnContainerClickListener { loc -> getPosition(loc.point) }
        user_location_fab.setOnClickListener {
            if (permissionLocation) {
                cameraUserPosition()
                followUserLocation = true
            } else {
                checkPermission()
            }
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        setAnchor()
        userLocationView.pin.setIcon(fromResource(requireActivity(), R.drawable.user_arrow))
        userLocationView.arrow.setIcon(fromResource(requireActivity(), R.drawable.user_arrow))
        userLocationView.accuracyCircle.fillColor = TRANSPARENT
    }

    override fun onObjectRemoved(userLocationView: UserLocationView) {}

    override fun onObjectUpdated(userLocationView: UserLocationView, event: ObjectEvent) {}

    private fun getPosition(point: Point = routeStartLocation) {
        mapview.map.move(
            CameraPosition(point, ZOOM, ZERO_FLOAT, ZERO_FLOAT),
            Animation(Animation.Type.SMOOTH, ZERO_FLOAT),
            null
        )
    }

    private fun setAnchor() {
        val pointWith = (mapview.width * 0.5).toFloat()
        val pointHeight = (mapview.height * 0.5).toFloat()
        val pointF = PointF(pointWith, pointHeight )
        locationLayer.setAnchor( pointF,pointF)
        user_location_fab.setImageResource(R.drawable.ic_location_found)
        followUserLocation = false
    }

    private fun noAnchor() {
        locationLayer.resetAnchor()
        user_location_fab.setImageResource(R.drawable.ic_location_search)
    }

    private fun checkPermission() {
        if (!isPermissionGranted()) {
            permissionLocation = true
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private fun isPermissionGranted(): Boolean =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireActivity(),
            ACCESS_FINE_LOCATION
        )

    private fun observe() {
        locationPointsViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is List<*> -> {
                val loc: List<Locations> = data as List<Locations>
                recycler_list.apply {
                    locationPointsAdapter.loc = loc
                    adapter = locationPointsAdapter
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        finish: Boolean
    ) {
        if (finish) {
            if (followUserLocation) {
                setAnchor()
            }
        } else {
            if (!followUserLocation) {
                noAnchor()
            }
        }
    }
}