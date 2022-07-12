package com.pipe.codebox.data.point.update

import com.pipe.codebox.domain.repository.UpdateLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UpdateModule {

    @Binds
    fun bindUpdateSource(
        impl: UpdateRepositoryImpl,
    ): UpdateLocationRepository
}