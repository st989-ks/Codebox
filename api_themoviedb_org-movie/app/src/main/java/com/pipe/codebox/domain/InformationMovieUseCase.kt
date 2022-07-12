package com.pipe.codebox.domain

import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.presenter.base.BaseResult
import com.pipe.codebox.domain.repository.InformationMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InformationMovieUseCase @Inject constructor(
    private val informationMovieRepository: InformationMovieRepository
) {
    suspend fun invokeInformation(key: String)
            : Flow<BaseResult<InformationAll, Throwable>> {
        return informationMovieRepository.invokeResponseInformation(key)
    }
}