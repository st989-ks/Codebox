package com.pipe.codebox.data.point.save

import androidx.room.withTransaction
import com.pipe.codebox.data.room.LocationsDatabase
import com.pipe.codebox.data.room.dao.LocationsDao
import com.pipe.codebox.data.room.mappers.toEntity
import com.pipe.codebox.data.room.mappers.toModel
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.SaveLocationRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao,
) : SaveLocationRepository {

    override suspend fun invokeSave(locations: Locations): Flow<BaseResult<Boolean, Throwable>> {
        return flow {
            locationsDao.insert(locations.toEntity())
            emit(BaseResult.Success(true))
        }
    }
}

