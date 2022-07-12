package com.pipe.codebox.domain.repository

import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface InformationMovieRepository {
    suspend fun invokeResponseInformation(key: String)
            : Flow<BaseResult<InformationAll, Throwable>>
}