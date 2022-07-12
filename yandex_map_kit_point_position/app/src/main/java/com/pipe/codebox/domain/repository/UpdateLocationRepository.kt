package com.pipe.codebox.domain.repository

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import kotlinx.coroutines.flow.Flow

interface UpdateLocationRepository {
    suspend fun invokeUpdate(locations: Locations)
            : Flow<BaseResult<List<Locations>, Throwable>>
}