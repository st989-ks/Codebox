package com.pipe.codebox.data.point.locations

import com.pipe.codebox.data.room.dao.LocationsDao
import com.pipe.codebox.data.room.mappers.toModel
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.LocationsRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationsListRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao,
) : LocationsRepository {

    override suspend fun invokeGet(): Flow<BaseResult<List<Locations>, Throwable>> {
        return flow {
            emit(BaseResult.Success(locationsDao.selectAll().toModel()))
        }
    }
}

