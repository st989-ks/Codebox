package com.pipe.codebox.data.room.mappers

import com.pipe.codebox.data.room.model.RoomLocation
import com.pipe.codebox.domain.entity.Locations
import com.yandex.mapkit.geometry.Point

internal fun RoomLocation.toModel() = Locations(
    keyPoint = this.key,
    markerId = this.markerId,
    name = this.name,
    point = Point( this.latitude, this.longitude)
)


@JvmName("toModelRoomNoteList")
internal fun List<RoomLocation>.toModel(): List<Locations> = this.map { it.toModel() }
