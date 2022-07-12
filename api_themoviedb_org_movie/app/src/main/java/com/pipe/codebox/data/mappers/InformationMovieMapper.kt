package com.pipe.codebox.data.mappers

import com.pipe.codebox.data.models.MovieDetail
import com.pipe.codebox.domain.entity.InformationMovie
import com.pipe.codebox.extensions.POSTER_BASE_URL_INFORMATION
import com.pipe.codebox.extensions.voteAverageGetFiveAsDouble

internal fun MovieDetail.toEntity() = InformationMovie(
    posterPath = POSTER_BASE_URL_INFORMATION + this.posterPath,
    originalTitle = this.originalTitle,
    voteAverage = this.voteAverage.voteAverageGetFiveAsDouble(),
    voteCount = this.voteCount,
    originalLanguage = this.originalLanguage,
    overview = this.overview
)