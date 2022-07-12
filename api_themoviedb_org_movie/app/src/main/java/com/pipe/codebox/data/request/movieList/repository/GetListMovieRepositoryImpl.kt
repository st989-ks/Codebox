package com.pipe.codebox.data.request.movieList.repository

import com.pipe.codebox.data.mappers.toEntity
import com.pipe.codebox.data.request.movieList.api.MovieListApi
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.domain.repository.GetListMovieRepository
import com.pipe.codebox.extensions.replacingSpaces
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListMovieRepositoryImpl @Inject constructor(
    private val movieListApi: MovieListApi,
) : GetListMovieRepository {

    override suspend fun invokeResponse(
        request: String?,
        page: Int?
    ): Flow<BaseResult<List<MovieEntity>, Throwable>> {
        return flow {
            val getPage = page ?: 1
            val resp = if (request != null) {
                movieListApi.requestMovieSearch(request.replacingSpaces(), getPage)
            } else {
                movieListApi.requestMovieList(page = getPage)
            }
            val response = resp.results.toEntity(resp.page)
            emit(BaseResult.Success(response))
        }
    }
}

