package com.pipe.codebox.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pipe.codebox.BuildConfig
import com.pipe.codebox.R
import com.pipe.codebox.di.NavigatorModule
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorModule: NavigatorModule

    private lateinit var navHostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMap()
        initNavHostFragment()
    }

    private fun initMap(){
        MapKitFactory.setApiKey(BuildConfig.API_KEY)
    }

    private fun initNavHostFragment(){
        navHostFragment =
            requireNotNull(supportFragmentManager.findFragmentById(R.id.fragmentContainer))
        val navController = navHostFragment.findNavController()
        navigatorModule.navController = navController
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            navigatorModule.navigateBack()
        } else {
            finish()
        }
    }
}