package com.pipe.codebox.presenter.view

import androidx.lifecycle.viewModelScope
import com.pipe.codebox.domain.HomeValueUseCase
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeValueViewModel @Inject constructor(
    private val homeValueUseCase: HomeValueUseCase,
) : BaseViewModel() {

    fun requestVM() {
        viewModelScope.launch {
            homeValueUseCase.invoke()
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