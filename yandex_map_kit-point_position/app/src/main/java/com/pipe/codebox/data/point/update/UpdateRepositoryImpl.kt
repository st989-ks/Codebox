package com.pipe.codebox.data.point.update

import com.pipe.codebox.data.room.dao.LocationsDao
import com.pipe.codebox.data.room.mappers.toEntity
import com.pipe.codebox.data.room.mappers.toModel
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.UpdateLocationRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao,
) : UpdateLocationRepository {

    override suspend fun invokeUpdate(locations: Locations): Flow<BaseResult<List<Locations>, Throwable>> {
        return flow {
            val loc = locations.toEntity()
            locationsDao.updateLocations(loc.key!!, loc.markerId, loc.name)
            val update = locationsDao.selectAll().toModel()
            emit(BaseResult.Success(update))
        }
    }
}

