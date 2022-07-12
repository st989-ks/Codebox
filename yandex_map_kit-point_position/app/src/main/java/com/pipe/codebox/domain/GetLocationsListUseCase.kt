package com.pipe.codebox.domain

import com.pipe.codebox.domain.repository.LocationsRepository
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.Locations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsListUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend fun invokeGet()
            : Flow<BaseResult<List<Locations>, Throwable>> {
        return locationsRepository.invokeGet()
    }
}