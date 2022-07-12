package com.pipe.codebox.domain.repository

import com.pipe.codebox.domain.entity.DataClass
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface ClassesRepository {
    suspend fun invoke()
            : Flow<BaseResult<List<DataClass>, Throwable>>
}