package com.pipe.codebox.presenter.view

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.pipe.codebox.R
import com.pipe.codebox.domain.SaveLocationUseCase
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


class SaveLocationPointsViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase,
    private val application: Application
) : BaseViewModel() {

    fun startSave(locations: Locations) {
        viewModelScope.launch {
            saveLocationUseCase.invokeSave(locations)
                .onStart {
                    setLoading()
                }.catch {
                    hideLoading()
                    errorReg(it)
                }.collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> {
                            if (it.data) {
                                showToast(application.getString(R.string.the_label_is_saved))
                            } else {
                                showToast(application.getString(R.string.the_label_is_no_saved))
                            }
                        }
                        is BaseResult.Errors -> errorReg(it.error)
                    }
                }
        }
    }

}