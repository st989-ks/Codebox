package com.pipe.codebox.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pipe.codebox.R
import com.pipe.codebox.di.NavigatorModule
import com.pipe.codebox.extensions.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorModule: NavigatorModule

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private lateinit var navHostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavHostFragment()
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

    override fun onStop() {
        sharedPrefs.clearAfter()
        super.onStop()
    }
}