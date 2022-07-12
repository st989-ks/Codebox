package com.pipe.codebox.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pipe.codebox.di.NavigatorModule
import com.pipe.codebox.extensions.*
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    abstract val layoutId: Int

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var navigatorModule: NavigatorModule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

}