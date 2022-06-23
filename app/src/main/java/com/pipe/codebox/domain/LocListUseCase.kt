package com.pipe.codebox.domain

import com.pipe.codebox.domain.repository.LocRepository
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocListUseCase @Inject constructor(
    private val loansListRepository: LocRepository
) {
    suspend fun invoke()
            : Flow<BaseResult<List<Locations>, Throwable>> {
        return loansListRepository.invoke()
    }
}