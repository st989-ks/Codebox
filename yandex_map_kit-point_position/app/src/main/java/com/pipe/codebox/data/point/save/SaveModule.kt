package com.pipe.codebox.data.point.save

import com.pipe.codebox.domain.repository.SaveLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SaveModule {

    @Binds
    fun bindSaveSource(
        impl: SaveRepositoryImpl,
    ): SaveLocationRepository
}