package com.pipe.codebox.domain

import com.pipe.codebox.domain.entity.HomeValue
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.repository.HomeValueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeValueUseCase @Inject constructor(
    private val homeValueRepository: HomeValueRepository
) {
    suspend fun invoke()
            : Flow<BaseResult<HomeValue, Throwable>> {
        return homeValueRepository.invoke()
    }
}