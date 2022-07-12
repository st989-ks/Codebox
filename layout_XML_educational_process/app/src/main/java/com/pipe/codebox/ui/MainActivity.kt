package com.pipe.codebox.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.pipe.codebox.R
import com.pipe.codebox.di.NavigatorModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.element_bottom_navigation.*
import javax.inject.Inject

@SuppressLint("UseCompatLoadingForDrawables")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorModule: NavigatorModule

    private lateinit var navHostFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavHostFragment()
        initNavigationBar()
    }

    private fun initNavHostFragment() {
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

    private fun clearBackStack() {
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            navHostFragment.childFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }


    private fun initNavigationBar() {
        home.setOnClickListener { initClickHome() }
        classes.setOnClickListener { initClickClasses() }
        list.setOnClickListener { initClickList() }
        favourite.setOnClickListener { initClickFavorite() }

    }

    private fun initClickHome() {
        setStandard()
        title_home.visibility = View.VISIBLE
        icon_home.imageTintList = getColorStateList(R.color.green)
        view_color_home.background = getDrawable(R.drawable.dark_green_gradient_background)
        clearBackStack()
        navigatorModule.navigate(R.id.homeFragment)
    }

    private fun initClickClasses() {
        setStandard()
        title_classes.visibility = View.VISIBLE
        icon_classes.imageTintList = getColorStateList(R.color.green)
        view_color_classes.background = getDrawable(R.drawable.dark_green_gradient_background)
        clearBackStack()
        navigatorModule.navigate(R.id.classesFragment)
    }

    private fun initClickList() {
        setStandard()
        title_list.visibility = View.VISIBLE
        icon_list.imageTintList = getColorStateList(R.color.green)
        view_color_list.background = getDrawable(R.drawable.dark_green_gradient_background)
        clearBackStack()
        navigatorModule.navigate(R.id.emptyFragment)
    }

    private fun initClickFavorite() {
        setStandard()
        title_favourite.visibility = View.VISIBLE
        icon_favourite.imageTintList = getColorStateList(R.color.green)
        view_color_favourite.background = getDrawable(R.drawable.dark_green_gradient_background)
        clearBackStack()
        navigatorModule.navigate(R.id.emptyFragment)
    }

    private fun setStandard() {
        title_home.visibility = View.GONE
        title_classes.visibility = View.GONE
        title_list.visibility = View.GONE
        title_favourite.visibility = View.GONE

        icon_home.imageTintList = getColorStateList(R.color.white)
        icon_classes.imageTintList = getColorStateList(R.color.white)
        icon_list.imageTintList = getColorStateList(R.color.white)
        icon_favourite.imageTintList = getColorStateList(R.color.white)

        view_color_home.background = getDrawable(R.drawable.transparent_background)
        view_color_classes.background = getDrawable(R.drawable.transparent_background)
        view_color_list.background = getDrawable(R.drawable.transparent_background)
        view_color_favourite.background = getDrawable(R.drawable.transparent_background)
    }
}
