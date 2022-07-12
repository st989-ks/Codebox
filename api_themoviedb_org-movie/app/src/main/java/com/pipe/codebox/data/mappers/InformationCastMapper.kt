package com.pipe.codebox.data.mappers

import com.pipe.codebox.data.models.Credits
import com.pipe.codebox.domain.entity.InformationCast
import com.pipe.codebox.extensions.POSTER_BASE_URL_INFORMATION

internal fun Credits.toEntity(): List<InformationCast> {
    val listCast: MutableList<InformationCast> = mutableListOf()
    this.cast.forEach {
        var path: String? = null
        if (!it.profilePath.isNullOrEmpty()) path = POSTER_BASE_URL_INFORMATION + it.profilePath
        listCast.add(InformationCast(it.originalName, path))
    }
    return listCast
}



