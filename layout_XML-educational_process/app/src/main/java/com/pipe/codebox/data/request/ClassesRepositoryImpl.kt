package com.pipe.codebox.data.request

import com.pipe.codebox.data.common.examResponse
import com.pipe.codebox.data.common.listClassResponse
import com.pipe.codebox.data.common.listHomeWorkResponse
import com.pipe.codebox.data.mappers.toEntityDataClass
import com.pipe.codebox.data.mappers.toEntityExam
import com.pipe.codebox.data.mappers.toEntityHomeWork
import com.pipe.codebox.data.mappers.toEntityLessonHome
import com.pipe.codebox.domain.entity.DataClass
import com.pipe.codebox.domain.entity.HomeValue
import com.pipe.codebox.domain.repository.ClassesRepository
import com.pipe.codebox.domain.repository.HomeValueRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClassesRepositoryImpl @Inject constructor(
) : ClassesRepository {

    override suspend fun invoke()
    : Flow<BaseResult<List<DataClass>, Throwable>> {
        return flow {
            val response = listClassResponse.toEntityDataClass()
            emit(BaseResult.Success(response))
        }
    }
}

