package com.pipe.codebox.domain

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.SaveLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(
    private val saveLocationRepository: SaveLocationRepository
) {
    suspend fun invokeSave(locations: Locations)
            : Flow<BaseResult<Boolean, Throwable>> {
        return saveLocationRepository.invokeSave(locations)
    }
}