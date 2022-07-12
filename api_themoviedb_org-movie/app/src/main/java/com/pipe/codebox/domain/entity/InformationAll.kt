package com.pipe.codebox.domain.entity

data class InformationAll (
    val movie: InformationMovie,
    val cast: List<InformationCast>
    )