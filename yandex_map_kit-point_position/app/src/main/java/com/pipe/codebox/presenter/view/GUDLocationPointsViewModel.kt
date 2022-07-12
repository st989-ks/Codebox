package com.pipe.codebox.presenter.view

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.pipe.codebox.R
import com.pipe.codebox.domain.DeleteLocationUseCase
import com.pipe.codebox.domain.GetLocationsListUseCase
import com.pipe.codebox.domain.UpdateLocationUseCase
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


class GUDLocationPointsViewModel @Inject constructor(
    private val getLocationsListUseCase: GetLocationsListUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
) : BaseViewModel() {

    fun startGetList() {
        viewModelScope.launch {
            getLocationsListUseCase.invokeGet()
                .onStart {
                    setLoading()
                }.catch {
                    hideLoading()
                    errorReg(it)
                }.collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> successReg(it.data)
                        is BaseResult.Errors -> errorReg(it.error)
                    }
                }
        }
    }

    fun startUpdate(locations: Locations) {
        viewModelScope.launch {
            updateLocationUseCase.invokeUpdate(locations)
                .onStart {
                    setLoading()
                }.catch {
                    hideLoading()
                    errorReg(it)
                }.collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> successReg(it.data)
                        is BaseResult.Errors -> errorReg(it.error)
                    }
                }
        }
    }

    fun startDelete(key: Int) {
        viewModelScope.launch {
            deleteLocationUseCase.invokeUpdate(key)
                .onStart {
                    setLoading()
                }.catch {
                    hideLoading()
                    errorReg(it)
                }.collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> successReg(it.data)
                        is BaseResult.Errors -> errorReg(it.error)
                    }
                }
        }
    }

}