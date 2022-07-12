package com.pipe.codebox.domain.repository

import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.domain.entity.ListHealthData
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface HealthDataRepository {

    suspend fun invokeGet(): Flow<BaseResult<ListHealthData, Throwable, String>>
    suspend fun invokeSave(healthData: HealthData): Flow<BaseResult<HealthData, Throwable, String>>
    suspend fun invokeDelete(id: Long): Flow<BaseResult<ListHealthData, Throwable, String>>
}