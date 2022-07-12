package com.pipe.codebox.domain.repository

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface GetListMovieRepository {
    suspend fun invokeResponse(request: String? = null, page: Int? = null)
            : Flow<BaseResult<List<MovieEntity>, Throwable>>
}