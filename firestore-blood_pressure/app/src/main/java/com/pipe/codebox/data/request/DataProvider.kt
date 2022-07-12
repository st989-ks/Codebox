package com.pipe.codebox.data.request

import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.data.models.HealthDataResult

interface DataProvider {
    suspend fun subscribeToHealthData(): HealthDataResult
    suspend fun saveHealthData(healthData: HealthData): HealthData
    suspend fun deleteHealthData(id: Long)
    suspend fun getHealthDataById(id: String): HealthData
}