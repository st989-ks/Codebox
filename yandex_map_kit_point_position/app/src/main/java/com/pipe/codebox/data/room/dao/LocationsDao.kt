package com.pipe.codebox.data.room.dao

import androidx.room.*
import com.pipe.codebox.data.room.model.RoomLocation
import com.pipe.codebox.data.room.model.RoomLocation.Companion.IMAGE
import com.pipe.codebox.data.room.model.RoomLocation.Companion.KEY_POINT
import com.pipe.codebox.data.room.model.RoomLocation.Companion.TABLE_NAME
import com.pipe.codebox.data.room.model.RoomLocation.Companion.TEXT

@Dao
interface LocationsDao {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun selectAll(): List<RoomLocation>

    @Query("SELECT * FROM $TABLE_NAME WHERE $KEY_POINT = :key_point LIMIT 1")
    suspend fun select(key_point: Int): RoomLocation

    @Query("UPDATE $TABLE_NAME SET $IMAGE = (:markerId), $TEXT = (:name) WHERE $KEY_POINT IN (:key)")
    suspend fun updateLocations(key: Int, markerId: Int, name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg locations: RoomLocation)

    @Query("DELETE FROM $TABLE_NAME WHERE $KEY_POINT = (:keyPoint)")
    suspend fun deleteById(keyPoint: Int)
}