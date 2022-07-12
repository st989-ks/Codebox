package com.pipe.codebox.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pipe.codebox.data.room.dao.LocationsDao
import com.pipe.codebox.data.room.model.RoomLocation
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [RoomLocation::class], version = 1, exportSchema = false)
abstract class LocationsDatabase : RoomDatabase() {

    abstract val locationsDao: LocationsDao

    companion object Factory {

        fun build(@ApplicationContext appContext: Context): LocationsDatabase =
            Room.databaseBuilder(
                appContext,
                LocationsDatabase::class.java,
                "locations_database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}