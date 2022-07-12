package com.pipe.codebox.presenter.view

import androidx.lifecycle.viewModelScope
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.domain.HealthDataUseCase
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HealthHistoryViewModel @Inject constructor(
    private val healthDataUseCase: HealthDataUseCase,
) : BaseViewModel() {

    fun requestData() {
        viewModelScope.launch {
            healthDataUseCase.invokeGet()
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
                        is BaseResult.Message -> showToast(it.str)
                    }
                }
        }
    }

    fun saveData(healthData: HealthData) {
        viewModelScope.launch {
            healthDataUseCase.invokeSave(healthData)
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
                        is BaseResult.Message -> showToast(it.str)
                    }
                }
        }
    }

    fun deleteData(id: Long) {
        viewModelScope.launch {
            healthDataUseCase.invokeDelete(id)
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
                        is BaseResult.Message -> showToast(it.str)
                    }
                }
        }
    }
}