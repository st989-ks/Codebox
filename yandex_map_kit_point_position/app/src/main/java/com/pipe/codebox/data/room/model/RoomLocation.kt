package com.pipe.codebox.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(
    tableName = RoomLocation.TABLE_NAME,
    primaryKeys = [RoomLocation.KEY_POINT]
)
data class RoomLocation(
    @ColumnInfo(name = KEY_POINT) val key: Int?,
    @ColumnInfo(name = IMAGE) val markerId: Int,
    @ColumnInfo(name = TEXT) val name: String,
    @ColumnInfo(name = POINT_X) val latitude: Double,
    @ColumnInfo(name = POINT_Y) val longitude: Double,
) {
    companion object {
        const val TABLE_NAME = "locations"
        const val KEY_POINT = "key_point"
        const val TEXT = "text"
        const val IMAGE = "image"
        const val POINT_X = "latitude"
        const val POINT_Y = "longitude"
    }
}
