package com.pipe.codebox.domain

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.SaveLocationRepository
import com.pipe.codebox.domain.repository.UpdateLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateLocationUseCase @Inject constructor(
    private val updateLocationRepository: UpdateLocationRepository
) {
    suspend fun invokeUpdate(locations: Locations)
            : Flow<BaseResult<List<Locations>, Throwable>> {
        return updateLocationRepository.invokeUpdate(locations)
    }
}