package com.pipe.codebox.ui.fragment.map

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.pipe.codebox.R
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.presenter.view.GUDLocationPointsViewModel
import com.pipe.codebox.presenter.view.SaveLocationPointsViewModel
import com.pipe.codebox.ui.fragment.base.BaseFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

abstract class AbstractMapFragment : BaseFragment(), UserLocationObjectListener {

    protected var permissionLocation = false

    protected  var followUserLocation = false

    @Inject
    lateinit var gudLocationPointsViewModel: GUDLocationPointsViewModel

    @Inject
    lateinit var saveLocationPointsViewModel: SaveLocationPointsViewModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            Log.i(APP_TAG, getString(R.string.permission_ok))
        } else {
            Log.i(APP_TAG, getString(R.string.permission_no))
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

    protected fun checkPermission() {
        if (!isPermissionGranted()) {
            permissionLocation = true
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun isPermissionGranted(): Boolean =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    override fun onObjectRemoved(userLocationView: UserLocationView) {}

    override fun onObjectUpdated(userLocationView: UserLocationView, event: ObjectEvent) {}
}