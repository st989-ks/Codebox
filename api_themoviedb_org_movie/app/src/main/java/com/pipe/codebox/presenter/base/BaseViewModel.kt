package com.pipe.codebox.presenter.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pipe.codebox.extensions.ERROR_TAG

abstract class BaseViewModel : ViewModel(){

    private val _state = MutableLiveData<State>(State.Init)
    val state: LiveData<State> = _state

    fun resetState() {
        _state.value = State.Init
    }

    protected fun setLoading() {
        _state.value = State.IsLoading(true)
    }

    protected fun hideLoading() {
        _state.value = State.IsLoading(false)
    }

    protected fun successReg(entity: Any) {
        _state.value = State.Success(entity)
    }

    protected fun showToast(message: String) {
        _state.value = State.ShowToast(message)
    }

    protected fun errorReg(throwable: Throwable) {
        _state.value = State.Error(throwable)
        Log.e(ERROR_TAG, throwable.stackTraceToString())
    }
}