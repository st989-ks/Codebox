package com.pipe.codebox.domain

import com.pipe.codebox.domain.entity.DataClass
import com.pipe.codebox.domain.entity.HomeValue
import com.pipe.codebox.domain.repository.ClassesRepository
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.repository.HomeValueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClassesUseCase @Inject constructor(
    private val classesRepository: ClassesRepository
) {
    suspend fun invoke()
            : Flow<BaseResult<List<DataClass>, Throwable>> {
        return classesRepository.invoke()
    }
}