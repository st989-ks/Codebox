package com.pipe.codebox.data.request.informationMovie.repository

import com.pipe.codebox.data.mappers.toEntity
import com.pipe.codebox.data.request.informationMovie.api.InformationMovieApi
import com.pipe.codebox.data.request.movieList.api.MovieListApi
import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.domain.entity.InformationMovie
import com.pipe.codebox.domain.repository.InformationMovieRepository
import com.pipe.codebox.presenter.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InformationMovieRepositoryImpl @Inject constructor(
    private val informationMovieApi: InformationMovieApi,
) : InformationMovieRepository {

    override suspend fun invokeResponseInformation(key: String)
    : Flow<BaseResult<InformationAll, Throwable>> {
        return flow {
            val movieDetail = informationMovieApi.requestInformationMovie(key).toEntity()
            val castActorDetail = informationMovieApi.requestInformationActor(key).toEntity()
            emit(BaseResult.Success(InformationAll(movieDetail,castActorDetail)))
        }
    }
}

