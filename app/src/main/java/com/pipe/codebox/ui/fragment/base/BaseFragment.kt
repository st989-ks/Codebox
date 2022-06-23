package com.pipe.codebox.ui.fragment.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pipe.codebox.di.NavigatorModule
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.showLoader
import com.pipe.codebox.extensions.showToast
import com.pipe.codebox.presenter.base.State
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    @Inject
    lateinit var navigatorModule: NavigatorModule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    protected fun handleState(state: State) {
        when (state) {
            is State.ShowToast -> handleShow(state.message)
            is State.IsLoading -> handleLoading(state.isLoading)
            is State.Error -> handleError(state.throwable)
            is State.Success -> handleSuccess(state.success)
            is State.Init -> Unit
        }
    }

    private fun handleShow(message: String) {
        requireActivity().showToast(message)
    }

    private fun handleLoading(isLoading: Boolean) {
        requireActivity().showLoader(isLoading)
    }

    private fun handleError(error: Throwable) {
        Log.e(APP_TAG, error.toString())
    }

    abstract fun handleSuccess(data: Any)
}