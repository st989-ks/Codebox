package com.pipe.codebox.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pipe.codebox.domain.GetListUseCase
import com.pipe.codebox.domain.entity.PostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class GetListViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
) : ViewModel() {

    fun getFileDataStream(): Flow<PagingData<PostEntity>> =
        getListUseCase.invokeRequest().cachedIn(viewModelScope)
}