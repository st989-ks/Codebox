package com.pipe.codebox.domain.repository

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import kotlinx.coroutines.flow.Flow

interface LocRepository {
    suspend fun invoke()
            : Flow<BaseResult<List<Locations>, Throwable>>
}