package com.pipe.codebox.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pipe.codebox.di.NavigatorModule
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    lateinit var bundle: Bundle

    @Inject
    lateinit var navigatorModule: NavigatorModule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bundle = Bundle()
        return inflater.inflate(layoutId, container, false)
    }
}