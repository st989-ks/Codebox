package com.pipe.codebox.domain.repository

import com.pipe.codebox.domain.entity.HomeValue
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface HomeValueRepository {
    suspend fun invoke()
            : Flow<BaseResult<HomeValue, Throwable>>
}