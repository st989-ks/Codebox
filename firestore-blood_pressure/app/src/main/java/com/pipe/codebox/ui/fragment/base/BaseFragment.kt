package com.pipe.codebox.ui.fragment.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pipe.codebox.di.NavigatorModule
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.SharedPrefs
import com.pipe.codebox.presenter.base.State
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    lateinit var bundle: Bundle

    lateinit var binding: View

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var navigatorModule: NavigatorModule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bundle = Bundle()
        binding = inflater.inflate(layoutId, container, false)
        return binding
    }

    protected fun handleState(state: State) {
        when (state) {
            is State.ShowToast -> handleShow(state.message)
            is State.IsLoading -> handleLoading(state.isLoading)
            is State.Error -> handleError(state.throwable)
            is State.Success -> handleSuccess(state.success)
            is State.Init -> Unit
        }
    }

    abstract fun handleShow(message: String)

    private fun handleLoading(isLoading: Boolean) {
        Log.i(APP_TAG, isLoading.toString())
    }

    private fun handleError(error: Throwable) {
        Log.e(APP_TAG, error.toString())
    }
    abstract fun handleSuccess(data: Any)
}