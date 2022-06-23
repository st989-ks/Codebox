package com.pipe.codebox.presenter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.codebox.domain.LocListUseCase
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationPointsViewModel @Inject constructor(
    private val locListUseCase: LocListUseCase,
) : BaseViewModel() {

    fun start() {
        viewModelScope.launch {
            locListUseCase.invoke()
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