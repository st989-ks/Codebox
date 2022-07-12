package com.pipe.codebox.domain

import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.domain.repository.GetListMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListMovieUseCase @Inject constructor(
    private val getListMovieRepository: GetListMovieRepository
) {
    suspend fun invokeRequest(request: String? = null, page: Int? = null)
            : Flow<BaseResult<List<MovieEntity>, Throwable>> {
        return getListMovieRepository.invokeResponse(request, page)
    }
}