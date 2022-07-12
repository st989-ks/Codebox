package com.pipe.codebox.data.point.delete

import com.pipe.codebox.data.room.dao.LocationsDao
import com.pipe.codebox.data.room.mappers.toModel
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.DeleteLocationRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao,
) : DeleteLocationRepository {

    override suspend fun invokeDelete(key: Int): Flow<BaseResult<List<Locations>, Throwable>>  {
        return flow {
            locationsDao.deleteById(key)
            val update = locationsDao.selectAll().toModel()
            emit(BaseResult.Success(update))
        }
    }
}


