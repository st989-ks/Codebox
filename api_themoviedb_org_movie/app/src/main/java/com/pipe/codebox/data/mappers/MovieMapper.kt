package com.pipe.codebox.data.mappers

import com.pipe.codebox.data.models.Movie
import com.pipe.codebox.domain.entity.MovieEntity
import com.pipe.codebox.extensions.POSTER_BASE_URL_INFORMATION
import com.pipe.codebox.extensions.voteAverageGetFiveAsString

internal fun Movie.toEntity(page: Int) = MovieEntity(
    page = page,
    Id = this.id.toInt(),
    originalTitle = this.originalTitle,
    posterPath = POSTER_BASE_URL_INFORMATION + this.posterPath,
    voteAverage = this.voteAverage.voteAverageGetFiveAsString(),
)

internal fun List<Movie>.toEntity(page: Int): List<MovieEntity> = this.map { it.toEntity(page) }