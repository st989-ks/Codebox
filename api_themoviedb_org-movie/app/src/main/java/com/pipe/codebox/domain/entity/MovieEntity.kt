package com.pipe.codebox.domain.entity


data class MovieEntity(
    val page: Int,
    val Id: Int,
    val originalTitle: String,
    val posterPath: String,
    val voteAverage: Double,
)
