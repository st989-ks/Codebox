package com.pipe.codebox.domain

import android.app.Application
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.domain.entity.ListHealthData
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.repository.HealthDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HealthDataUseCase @Inject constructor(
    private val healthDataRepository: HealthDataRepository,
    private val application: Application
) {

    suspend fun invokeGet()
            : Flow<BaseResult<ListHealthData, Throwable, String>> {
        return healthDataRepository.invokeGet()
    }

    suspend fun invokeSave(healthData: HealthData)
            : Flow<BaseResult<HealthData, Throwable, String>> {
        if (healthData.upperBloodPressure.isEmpty()) return flow {
            emit(BaseResult.Message(application.getString(R.string.throwable_upper_blood_pressure)))
        }
        if (healthData.lowerBloodPressure.isEmpty()) return flow {
            emit(BaseResult.Message(application.getString(R.string.throwable_lower_blood_pressure)))
        }
        if (healthData.pulse.isEmpty()) return flow {
            emit(BaseResult.Message(application.getString(R.string.throwable_pulse)))
        }

        return healthDataRepository.invokeSave(healthData)
    }

    suspend fun invokeDelete(id: Long)
            : Flow<BaseResult<ListHealthData, Throwable, String>> {
        return healthDataRepository.invokeDelete(id)
    }
}