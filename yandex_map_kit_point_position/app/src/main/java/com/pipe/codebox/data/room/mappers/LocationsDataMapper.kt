package com.pipe.codebox.data.room.mappers

import com.pipe.codebox.data.room.model.RoomLocation
import com.pipe.codebox.domain.entity.Locations

internal fun Locations.toEntity() = RoomLocation(
    key = this.keyPoint,
    markerId = this.markerId,
    name = this.name,
    latitude = this.point.latitude,
    longitude = this.point.longitude,
)


@JvmName("toEntityNoteList")
internal fun List<Locations>.toEntity(): List<RoomLocation> = this.map { it.toEntity() }
