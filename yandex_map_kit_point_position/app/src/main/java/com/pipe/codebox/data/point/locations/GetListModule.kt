package com.pipe.codebox.data.point.locations

import com.pipe.codebox.domain.repository.LocationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GetListModule {

    @Binds
    fun bindLocationsListSource(
        impl: LocationsListRepositoryImpl,
    ): LocationsRepository
}