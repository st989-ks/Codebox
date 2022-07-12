package com.pipe.codebox.data.request

import com.pipe.codebox.data.common.examResponse
import com.pipe.codebox.data.common.listClassResponse
import com.pipe.codebox.data.common.listHomeWorkResponse
import com.pipe.codebox.data.mappers.toEntityExam
import com.pipe.codebox.data.mappers.toEntityHomeWork
import com.pipe.codebox.data.mappers.toEntityLessonHome
import com.pipe.codebox.domain.entity.HomeValue
import com.pipe.codebox.domain.repository.HomeValueRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeValueRepositoryImpl @Inject constructor(
) : HomeValueRepository {

    override suspend fun invoke()
            : Flow<BaseResult<HomeValue, Throwable>> {
        return flow {
            val response = HomeValue(
                examResponse.toEntityExam(),
                listClassResponse.toEntityLessonHome(),
                listHomeWorkResponse.toEntityHomeWork()
            )
            emit(BaseResult.Success(response))
        }
    }
}

