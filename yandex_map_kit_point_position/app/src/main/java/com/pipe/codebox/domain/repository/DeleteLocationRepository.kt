package com.pipe.codebox.domain.repository

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import kotlinx.coroutines.flow.Flow

interface DeleteLocationRepository {
    suspend fun invokeDelete(key: Int)
            : Flow<BaseResult<List<Locations>, Throwable>>
}