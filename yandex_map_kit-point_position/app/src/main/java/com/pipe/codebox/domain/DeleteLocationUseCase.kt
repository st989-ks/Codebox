package com.pipe.codebox.domain

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.DeleteLocationRepository
import com.pipe.codebox.domain.repository.SaveLocationRepository
import com.pipe.codebox.domain.repository.UpdateLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(
    private val deleteLocationRepository: DeleteLocationRepository
) {
    suspend fun invokeUpdate(key: Int)
            : Flow<BaseResult<List<Locations>, Throwable>>{
        return deleteLocationRepository.invokeDelete(key)
    }
}