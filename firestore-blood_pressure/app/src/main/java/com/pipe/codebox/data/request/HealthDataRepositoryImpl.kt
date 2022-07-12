package com.pipe.codebox.data.request

import android.app.Application
import android.util.Log
import com.pipe.codebox.R
import com.pipe.codebox.data.models.HealthDataResult
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.domain.entity.ListHealthData
import com.pipe.codebox.domain.repository.HealthDataRepository
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HealthDataRepositoryImpl @Inject constructor(
    private val dataProvider: DataProvider,
    private val application: Application
) : HealthDataRepository {

    override suspend fun invokeGet(): Flow<BaseResult<ListHealthData, Throwable, String>> {
       return flow {
           when (val result = dataProvider.subscribeToHealthData()){
                   is HealthDataResult.Success<*> -> {
                       val response = (result.data as List<HealthData>).sortedBy { it.id }
                       emit(BaseResult.Success(ListHealthData(response)))
                   }
                   is HealthDataResult.Error -> {
                       emit(BaseResult.Errors(Throwable()))
                   }
                   else -> {
                       emit(BaseResult.Message(application.getString(R.string.something_went_wrong)))
                   }
               }
       }
    }

    override suspend fun invokeSave(healthData: HealthData): Flow<BaseResult<HealthData, Throwable, String>> {
        return flow {
            val result = dataProvider.saveHealthData(healthData)
            emit(BaseResult.Success(result))
        }
    }

    override suspend fun invokeDelete(id: Long): Flow<BaseResult<ListHealthData, Throwable, String>> {
        return flow {
            Log.i(APP_TAG, "$id")
            val result = dataProvider.deleteHealthData(id)
            Log.i(APP_TAG, "$result")
            emit(BaseResult.Message(application.getString(R.string.deletet)))
        }
    }
}