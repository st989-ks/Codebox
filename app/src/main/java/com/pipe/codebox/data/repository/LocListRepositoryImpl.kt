package com.pipe.codebox.data.repository

import com.pipe.codebox.data.filelists.ActionFile
import com.pipe.codebox.domain.entity.Locations
import com.pipe.codebox.domain.repository.LocRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocListRepositoryImpl @Inject
constructor(
    private val actionFile: ActionFile
) : LocRepository {

    override suspend fun invoke(): Flow<BaseResult<List<Locations>, Throwable>> {
        return flow {
            val response: List<Locations>
            response = actionFile.readContactsFiles()
            emit(BaseResult.Success(response))
        }
    }
}

