package com.pipe.codebox.di

import android.content.Context
import com.pipe.codebox.data.room.LocationsDatabase
import com.pipe.codebox.data.room.dao.LocationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationsDatabaseModule {

    @Provides
    fun provideLocationsDao(locationsDb: LocationsDatabase): LocationsDao = locationsDb.locationsDao

    @Provides
    @Singleton
    fun provideNoteDb(
        @ApplicationContext appContext: Context
    ): LocationsDatabase = LocationsDatabase.build(appContext)
}