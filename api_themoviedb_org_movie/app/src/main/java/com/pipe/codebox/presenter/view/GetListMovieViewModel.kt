package com.pipe.codebox.presenter.view

import androidx.lifecycle.viewModelScope
import com.pipe.codebox.domain.GetListMovieUseCase
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.presenter.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetListMovieViewModel @Inject constructor(
    private val getListMovieUseCase: GetListMovieUseCase,
) : BaseViewModel() {

    private var searchJob: Job? = null


    fun requestMovies(request: String? = null, page: Int? = null) {
        viewModelScope.launch {
            getListMovieUseCase.invokeRequest(request, page)
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

    fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            requestMovies(searchText)
        }
    }

}